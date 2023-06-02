package com.example.myfavoritemovie.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table")
data class Item(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "movie length")
    val length: String,
    @ColumnInfo(name = "photo URL")
    val photo: String?,
    @ColumnInfo(name = "number of stars")
    val stars: Int)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


//object ItemManger{
//    val items : MutableList<Item> = mutableListOf()
//
//    fun add (item:Item){
//        items.add(item)
//    }
//
//    fun remove(index:Int){
//        items.removeAt(index)
//    }
//}
