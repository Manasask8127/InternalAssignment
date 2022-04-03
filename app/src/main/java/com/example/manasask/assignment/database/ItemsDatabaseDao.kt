package com.example.manasask.assignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface ItemsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Items)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<Items>)

//    @Query("SELECT * FROM ITEMS_TABLE")
//    fun getItems():LiveData<List<Items>>

    @Query("SELECT * FROM ITEMS_TABLE WHERE item_category=:itemcategory")
    fun getItems(itemcategory: String): List<Items>


    @Query("SELECT * FROM ITEMS_TABLE WHERE item_category=:itemcategory ORDER BY item_name ASC")
    fun getItemsAsc(itemcategory: String): List<Items>

    @Query("SELECT * FROM ITEMS_TABLE ORDER BY item_name DESC")
    fun getItemsDsc(): LiveData<List<Items>>


}