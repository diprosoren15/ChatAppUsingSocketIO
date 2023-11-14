package com.example.chatappusingsocketio

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.chatappusingsocketio.model.MessageEntity

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages")
    fun getAllMessages() : MutableLiveData<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

}