package com.example.smalltalk.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserObject::class, ChatObject::class], version = 1)
    abstract class AppDatabase: RoomDatabase() {
    abstract fun userDAO(): UserDAO //for å få tilgang til DAO som er laget
    abstract fun chatDAO(): ChatDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
