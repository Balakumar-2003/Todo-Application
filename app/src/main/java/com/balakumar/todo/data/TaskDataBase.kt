package com.balakumar.todo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskDetails::class], version = 1)
@TypeConverters(Converter::class)
abstract class TaskDataBase:RoomDatabase() {
    abstract fun getTaskDao():TaskDao

    companion object{
        @Volatile
        private var INSTANCE:TaskDataBase?=null

        fun getDataBase(context:Context):TaskDataBase?{
            return INSTANCE?: synchronized(this){
                val instance= Room.databaseBuilder(context.applicationContext,
                    TaskDataBase::class.java,
                    "task_database").build()
                INSTANCE = instance
                INSTANCE
            }
        }
    }
}