package com.example.manasask.assignment.database

import androidx.room.*
import com.example.manasask.assignment.DateConverter
import java.sql.Date


@Entity(tableName = "items_table")
data class Items(

    @PrimaryKey(autoGenerate = true)
    var itemId: Long = 0L,

    @ColumnInfo(name = "item_name")
    var itemName: String = "",

    @ColumnInfo(name = "item_image_path")
    var itemImagePath: String = "",

    @ColumnInfo(name = "item_category")
    var itemCategory: String = "",

//    @ColumnInfo(name = "item_added_date")
//    var itemAddedDate :  Date

)


