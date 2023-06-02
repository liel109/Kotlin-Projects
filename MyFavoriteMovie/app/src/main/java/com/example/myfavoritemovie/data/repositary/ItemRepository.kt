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

    fun addItem(item: Item){
        itemDao?.addItem(item)
    }

    fun deleteItem(item: Item){
        itemDao?.deleteItem(item)
    }

    fun deleteAll(){
        itemDao?.deleteAll()
    }
}