package com.example.chatappusingsocketio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.chatappusingsocketio.ChatAppDatabase
import com.example.chatappusingsocketio.model.MessageEntity
import com.example.chatappusingsocketio.model.TypeMessage
import com.example.chatappusingsocketio.others.Event
import com.example.chatappusingsocketio.others.Resource
import com.example.chatappusingsocketio.repository.ChatAppRepository
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URISyntaxException

class ChatAppViewModel(application: Application) : AndroidViewModel(application) {
    private val _textLiveData = MutableLiveData<Event<Resource<MessageEntity>>>()
    val textLiveData get() = _textLiveData

    private var socket: Socket
//    private val repository: ChatAppRepository


    init {
//        repository = ChatAppRepository(application)
//        viewModelScope.launch {
//            repository.getMessages()
//        }
        try {
            socket = IO.socket("http://10.0.2.2:3000")
            socket.on("receiveMessage") { args ->
                if (args[0] != null) {
                    val data = args[0] as JSONObject
                    val msg = data.getString("message")
                    val id = data.getString("id")
                    if (id == socket.id()) {
                        _textLiveData.postValue(
                            Event(
                                Resource.Success(
                                    MessageEntity(1, msg, TypeMessage.TRUE)
                                )
                            )
                        )
//                        viewModelScope.launch {
//                            repository.addMessage(MessageEntity(id.toLong(), msg, TypeMessage.TRUE))
//                        }
                    } else {
                        _textLiveData.postValue(
                            Event(
                                Resource.Error(
                                    MessageEntity(1, msg, TypeMessage.FALSE)
                                )
                            )
                        )
//                        viewModelScope.launch {
//                            repository.addMessage(
//                                MessageEntity(
//                                    id.toLong(),
//                                    msg,
//                                    TypeMessage.FALSE
//                                )
//                            )
                    }
                }
            }
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
        }
    }

    fun connect() {
        socket.connect()
    }

    fun disconnect() {
        socket.disconnect()
    }

    fun sendMessage(message: String) {
        socket.emit("sendMessage", message)
    }

//    fun getPreviousMessages() {
//        viewModelScope.launch {
//            repository.getMessages()
//        }
//
//    }
}