package com.example.chatappusingsocketio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id:Long,
    val message : String,
    val messageType : TypeMessage
)

enum class TypeMessage{
    TRUE,FALSE
}
