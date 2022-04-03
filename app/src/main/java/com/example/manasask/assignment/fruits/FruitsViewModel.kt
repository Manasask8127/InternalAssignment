package com.example.manasask.assignment.fruits

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.manasask.assignment.database.Items
import com.example.manasask.assignment.database.ItemsDatabase
import com.example.manasask.assignment.filterItemsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FruitsViewModel(application: Application) :
    AndroidViewModel(application) {

    val database = ItemsDatabase.getInstance(application)

    private var fruitsList = listOf<Items>()

    private val _itemsFruits = MutableLiveData<List<Items>>()
    val itemsFruits: LiveData<List<Items>>
        get() = _itemsFruits


    private fun getVegetableItemsFromDatabase() {
        viewModelScope.launch {
            launch(Dispatchers.IO) {

                _itemsFruits.postValue(database.itemsDatabaseDao.getItems("fruit"))

                fruitsList =
                    database.itemsDatabaseDao.getItems("fruit")
                Log.d("get item from db", "" + fruitsList)
            }


        }
    }

    init {
        getVegetableItemsFromDatabase()
    }

    //filter out fruits
    fun filterList(string: String): List<Items> {
        Log.d("FruitsViewModel", fruitsList.toString())
        return filterItemsList(fruitsList, string)
    }

    //sorting of fruits
    fun sortingFruitsList(): List<Items> {
        return fruitsList.sortedBy { it.itemName }
    }

}