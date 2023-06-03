package com.example.myfavoritemovie.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myfavoritemovie.data.model.Item

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(item: Item)

    @Query("SELECT * FROM `table`")
    fun getItems() : LiveData<List<Item>>

    @Query("SELECT * FROM `table` WHERE title LIKE :title")
    suspend fun getItems(title: String) : Item

    @Query("DELETE FROM `table`")
    suspend fun deleteAll()
}