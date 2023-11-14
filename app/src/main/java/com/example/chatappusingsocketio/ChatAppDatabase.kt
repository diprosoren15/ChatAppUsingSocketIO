package com.example.chatappusingsocketio

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.chatappusingsocketio.model.MessageEntity

@Database(entities = [MessageEntity::class], version = 1, exportSchema = false)
abstract class ChatAppDatabase : RoomDatabase() {

    abstract fun messageDao() : MessageDao
    companion object {

        private var instance: ChatAppDatabase? = null

        fun getDatabase(context: Context): ChatAppDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ChatAppDatabase::class.java,
                        "chat_app_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance!!
            }
        }

    }

}