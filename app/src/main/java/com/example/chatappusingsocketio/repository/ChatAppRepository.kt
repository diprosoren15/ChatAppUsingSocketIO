package com.example.chatappusingsocketio.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatappusingsocketio.ChatAppDatabase
import com.example.chatappusingsocketio.MessageDao
import com.example.chatappusingsocketio.model.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatAppRepository(application: Application) {

    private val messageDao: MessageDao

    init {
        val database = ChatAppDatabase.getDatabase(application)
        messageDao = database.messageDao()
    }

    // Suspend function to fetch all messages using coroutines
    suspend fun getMessages() :MutableLiveData<List<MessageEntity>> {
        return withContext(Dispatchers.IO) {
            messageDao.getAllMessages()
        }
    }

    // Suspend function to send a message and insert it into the database
    suspend fun sendMessage(message: MessageEntity) {
        withContext(Dispatchers.IO) {
            messageDao.insertMessage(message)
        }
    }

}