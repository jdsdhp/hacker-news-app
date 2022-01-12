package com.jesusd0897.hackernews.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jesusd0897.hackernews.data.database.RoomAppDatabase.Companion.DB_VERSION
import com.jesusd0897.hackernews.data.model.Story

@Database(
    entities = [Story::class],
    version = DB_VERSION,
    exportSchema = false,
)
internal abstract class RoomAppDatabase : RoomDatabase(), AppDatabase {

    internal companion object {

        private const val DB_NAME = "data.db"
        internal const val DB_VERSION = 1

        // For Singleton instantiation
        @Volatile
        private var instance: RoomAppDatabase? = null

        internal fun getInstance(context: Context): RoomAppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): RoomAppDatabase =
            Room.databaseBuilder(context, RoomAppDatabase::class.java, DB_NAME).build()

    }

}