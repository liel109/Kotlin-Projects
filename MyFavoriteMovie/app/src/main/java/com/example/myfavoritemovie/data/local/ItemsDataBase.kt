package com.example.myfavoritemovie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myfavoritemovie.data.model.Item

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class ItemsDataBase : RoomDatabase(){

    abstract fun itemsDao() : ItemDao

    companion object{

        @Volatile
        private var instance : ItemsDataBase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(ItemsDataBase::class.java) {
            Room.databaseBuilder(context.applicationContext,
                ItemsDataBase::class.java,"items_database")
                .build().also { instance = it }
        }
    }
}