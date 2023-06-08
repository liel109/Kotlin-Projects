package com.example.myfavoritemovie.data.repositary

import android.app.Application
import com.example.myfavoritemovie.data.local.ItemDao
import com.example.myfavoritemovie.data.local.ItemsDataBase
import com.example.myfavoritemovie.data.model.Item

class ItemRepository(application: Application) {

    private var itemDao : ItemDao?

    init {
        val db = ItemsDataBase.getDatabase(application)
        itemDao = db.itemsDao()
    }

    fun getItems() = itemDao?.getItems()

    suspend fun addItem(item: Item){
        itemDao?.addItem(item)
    }

//    fun addItem(item: Item){
//        itemDao?.addItem(item)
//    }

    suspend fun deleteItem(item: Item){
        itemDao?.deleteItem(item)
    }

//    fun deleteItem(item: Item){
//        itemDao?.deleteItem(item)
//    }

    suspend fun deleteAll(){
        itemDao?.deleteAll()
    }

    suspend fun updateItem(item: Item){
        itemDao?.updateItem(item)
    }

//    fun deleteAll(){
//        itemDao?.deleteAll()
//    }
}