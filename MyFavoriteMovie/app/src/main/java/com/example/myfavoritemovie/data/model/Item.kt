package com.example.myfavoritemovie.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table")
data class Item(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description : String,
    @ColumnInfo(name = "movie length")
    var length: Int,
    @ColumnInfo(name = "photo URL")
    var photo: String,
    @ColumnInfo(name = "number of stars")
    var stars: Int)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}



