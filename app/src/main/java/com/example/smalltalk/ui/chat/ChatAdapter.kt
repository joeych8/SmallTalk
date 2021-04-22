package com.example.smalltalk.ui.chat

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.example.smalltalk.database.ChatObject
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(private var dataSet: List<ChatObject>, private val userId: String
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = ChatBubbleView(parent.context)

        view.layoutParams = ViewGroup.LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
        )

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chatObject = dataSet[position]

        holder.view.setChatText(chatObject.message)

        val dateOfMessage = Date(chatObject.timestamp)
        val calendar = Calendar.getInstance()
        calendar.time = dateOfMessage
        val timeFormat = SimpleDateFormat("HH:mm dd-MMM-yy")
        val timeStampText = timeFormat.format(calendar.time)
        holder.view.setTimeStampText(timeStampText)

        holder.view.setAuthorText(chatObject.userName.capitalize())
        holder.view.setSelfAuthor(chatObject.userId == userId)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newData: List<ChatObject>) {
        dataSet = newData
        notifyDataSetChanged()
    }

    fun addInstance(chatObject: ChatObject) {
        updateData(dataSet + chatObject)
    }

    inner class ChatViewHolder(val view: ChatBubbleView) : RecyclerView.ViewHolder(view)
}
