package com.example.manasask.assignment.database

import android.content.Context
import android.net.Uri
import android.provider.Settings
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Database(entities = [Items::class], version = 1, exportSchema = false)
abstract class ItemsDatabase : RoomDatabase() {

    abstract val itemsDatabaseDao: ItemsDatabaseDao

    companion object {
        @Volatile
        private var Instance: ItemsDatabase? = null

        fun getInstance(context: Context): ItemsDatabase {
            synchronized(this) {
                var instance = Instance
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemsDatabase::class.java,
                        "items_database"
                    ).addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            GlobalScope.launch {
                                withContext(Dispatchers.IO) {
                                    getInstance(context).itemsDatabaseDao.insertAll(addAllItems())
                                }
                            }
                        }
                    })
                        .fallbackToDestructiveMigration()
                        .build()
                    Instance = instance
                }
                return instance

            }
        }
    }


}

fun addAllItems(): List<Items> {
    return arrayListOf(
        Items(
            itemName = "Carrot",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/carrot")
                .toString(),
            itemCategory = "vegetable"
        ),
        Items(
            itemName = "bellpaper",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/bellpapper")
                .toString(),
            itemCategory = "vegetable"
        ),
        Items(
            itemName = "Tomato",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/tomato")
                .toString(),
            itemCategory = "vegetable"
        ),
        Items(
            itemName = "Onion",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/onion")
                .toString(),
            itemCategory = "vegetable"
        ),
        Items(
            itemName = "Cabbage",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/cabbage")
                .toString(),
            itemCategory = "vegetable"
        ),
        Items(
            itemName = "Dragon fruit",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/dragonfruit")
                .toString(),
            itemCategory = "fruit"
        ),
        Items(
            itemName = "Guava",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/guava")
                .toString(),
            itemCategory = "fruit"
        ),
        Items(
            itemName = "Orange",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/orange")
                .toString(),
            itemCategory = "fruit"
        ),
        Items(
            itemName = "PineApple",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/pineapple")
                .toString(),
            itemCategory = "fruit"
        ),
        Items(
            itemName = "Strawberry",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/strawberry")
                .toString(),
            itemCategory = "fruit"
        ),
        Items(
            itemName = "Candy",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/candy")
                .toString(),
            itemCategory = "snack"
        ),
        Items(
            itemName = "Chakali",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/chakli")
                .toString(),
            itemCategory = "snack"
        ),
        Items(
            itemName = "Chocoball",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/chocoball")
                .toString(),
            itemCategory = "snack"
        ),
        Items(
            itemName = "Chips",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/chips")
                .toString(),
            itemCategory = "snack"
        ),
        Items(
            itemName = "Gems",
            itemImagePath = Uri.parse("android.resource://com.example.manasask.assignment/drawable/gems")
                .toString(),
            itemCategory = "snack"
        )
    )

}




