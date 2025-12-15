package com.example.todo_mindora_app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo_mindora_app.data.local.Dao.TaskDao
import com.example.todo_mindora_app.data.local.Dao.PomodoroDao
import com.example.todo_mindora_app.data.local.entity.TaskEntity
import com.example.todo_mindora_app.data.local.entity.PomodoroEntity

@Database(entities = [TaskEntity::class, PomodoroEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun pomodoroDao(): PomodoroDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mindora_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}