package com.example.chatappusingsocketio.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappusingsocketio.databinding.ReceiveMessageBinding
import com.example.chatappusingsocketio.databinding.SendMessageBinding
import com.example.chatappusingsocketio.model.MessageEntity
import com.example.chatappusingsocketio.model.TypeMessage

class MessageAdapter(private val messages : MutableList<MessageEntity>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            MESSAGE_TYPE_SEND -> MessageSendViewHolder(
                SendMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))

            else -> MessageReceiveViewHolder(
                ReceiveMessageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ))
        }
    }

    fun addMessage(message: MessageEntity){
        messages.add(message)
        notifyItemInserted(messages.size)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return if(getItemViewType(position) == MESSAGE_TYPE_SEND){
            (holder as MessageSendViewHolder).bind(messages[position])
        } else {
            (holder as MessageReceiveViewHolder).bind(messages[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].messageType == TypeMessage.TRUE) MESSAGE_TYPE_SEND else MESSAGE_TYPE_RECEIVE
    }


    inner class MessageSendViewHolder(private val binding : SendMessageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data : MessageEntity){
            binding.sendMessage.text = data.message
        }
    }

    inner class MessageReceiveViewHolder(private val binding : ReceiveMessageBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: MessageEntity){
            binding.receiveMessage.text = data.message
        }
    }

    companion object {
        private const val MESSAGE_TYPE_SEND = 1
        private const val MESSAGE_TYPE_RECEIVE = 2
    }
}