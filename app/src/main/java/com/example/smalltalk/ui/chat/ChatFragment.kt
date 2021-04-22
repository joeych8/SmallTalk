package com.example.smalltalk.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalk.*
import com.example.smalltalk.database.AppDatabase
import com.example.smalltalk.database.ChatDAO
import com.example.smalltalk.database.ChatObject
import com.example.smalltalk.extensions.hideKeyboard
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.concurrent.fixedRateTimer


class ChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatInput: TextInputEditText
    private lateinit var sendButton: ImageButton
    private lateinit var timer: Timer


    private val user = UserManager.loggedInUser
    private lateinit var chatDAO: ChatDAO


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        recyclerView = view.findViewById(R.id.chat_recyclerView)
        chatInput = view.findViewById(R.id.chat_input)
        sendButton = view.findViewById(R.id.send_button)

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        chatDAO = AppDatabase.getDatabase(requireContext()).chatDAO()

        bindObservers()
        setButtonListeners()
        initRecyclerView()
        viewModel.getChatMessagesFromDatabase()
    }

    override fun onResume() {
        super.onResume()
        startChatFetchTimer()
    }

    private fun bindObservers() {
        viewModel.chatMessagesLiveData.observe(viewLifecycleOwner, { newList ->
            viewModel.saveChat(newList)

            activity?.runOnUiThread {
                chatAdapter.updateData(newList)
                scrollToBottom()
            }
        })
    }


    private fun setButtonListeners() {

        //gets text from edittext and sendt it to chat
        sendButton.setOnClickListener {
            val text = chatInput.text.toString()
            val chatObject = ChatObject(0, user.id, user.userName, text, Date().time)

            //message will only send if the editext is NOT empty.
            if (text.isNotEmpty()) {
                viewModel.sendChatMessage(
                        chatObject
                ) { success ->
                    if (success) {
                        activity?.hideKeyboard()
                        chatInput.setText("")
                        chatAdapter.addInstance(chatObject)
                        scrollToBottom()
                    } else {
                        scrollToBottom()
                        activity?.hideKeyboard()
                        Toast.makeText(
                                context,
                                "Something went wrong! Could not send message",
                                Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }


    private fun getChatMessages() {

        viewModel.getChatMessages(
                user.id
        ) {
            Toast.makeText(context, "Something went wrong! Could not download messages", Toast.LENGTH_LONG).show()
        }
    }

    //This function is created to set the layout of the recyclerview
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)

        chatAdapter = ChatAdapter(
                listOf(),
                user.id
        )
        recyclerView.adapter = chatAdapter
    }

    private fun startChatFetchTimer() {
        timer = fixedRateTimer("chatFetchTimer", false, 0L, 10 * 1000) {
            getChatMessages()
        }
    }

    private fun scrollToBottom() {
        recyclerView.scrollToPosition((recyclerView.adapter?.itemCount ?: 1) - 1)
    }


    override fun onPause() {
        super.onPause()
        timer.cancel()
    }
}
