package com.example.smalltalk.ui.chat

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextClock
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.marginEnd
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.example.smalltalk.R

class ChatBubbleView(context: Context) : ConstraintLayout(context) {

    private val chatLeftTextView: TextView
    private val authorLeftTextView: TextView
    private val chatRightTextView: TextView
    private val authorRightTextView: TextView
    private val timeStampLeft: TextView
    private val timeStampRight: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_bubble, this)

        chatLeftTextView = view.findViewById(R.id.chat_left_textView)
        authorLeftTextView = view.findViewById(R.id.chat_left_author_textView)
        chatRightTextView = view.findViewById(R.id.chat_right_textView)
        authorRightTextView = view.findViewById(R.id.chat_right_author_textView)
        timeStampLeft = view.findViewById(R.id.chat_timeStamp_left)
        timeStampRight = view.findViewById(R.id.chat_timeStamp_right)

    }

    fun setChatText(chatText: String) {
        chatLeftTextView.text = chatText
        chatRightTextView.text = chatText
    }

    fun setAuthorText(authorName: String) {
        authorLeftTextView.text = authorName
        authorRightTextView.text = authorName
    }

    fun setTimeStampText(timeStamp: String){
        timeStampLeft.text = timeStamp
        timeStampRight.text = timeStamp
    }

    // This function sets the position and color of senders chat bubble(right/yellow) and received chat bubble(left/gray)
    fun setSelfAuthor(isSelfAuthor: Boolean) {
        chatLeftTextView.isVisible = !isSelfAuthor
        authorLeftTextView.isVisible = !isSelfAuthor
        timeStampLeft.isVisible = !isSelfAuthor

        chatRightTextView.isVisible = isSelfAuthor
        authorRightTextView.isVisible = isSelfAuthor
        timeStampRight.isVisible = isSelfAuthor
    }
}
