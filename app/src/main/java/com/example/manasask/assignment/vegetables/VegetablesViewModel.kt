package com.example.manasask.assignment.vegetables

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

class VegetablesViewModel(application: Application) :
    AndroidViewModel(application) {

    val database = ItemsDatabase.getInstance(application)

    private var vegetableList = listOf<Items>()

    private val _itemsVegetables = MutableLiveData<List<Items>>()
    val itemsVegetables: LiveData<List<Items>>
        get() = _itemsVegetables

    private fun getVegetableItemsFromDatabase() {
        viewModelScope.launch {
            launch(Dispatchers.IO) {

                _itemsVegetables.postValue(database.itemsDatabaseDao.getItems("vegetable"))

                vegetableList = database.itemsDatabaseDao.getItems("vegetable")
                Log.d("get item from db", "" + vegetableList)
            }


        }
    }

    init {
        getVegetableItemsFromDatabase()
    }

    //filter out vegetables
    fun filterList(string: String): List<Items> {
        Log.d("VegetablesViewModel", vegetableList.toString())
        return filterItemsList(vegetableList, string)
    }

    //sort vegetable list
    fun sortingVegetableList(): List<Items> {
        return vegetableList.sortedBy { it.itemName }
    }
}