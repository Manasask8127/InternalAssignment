package com.example.manasask.assignment.snacks

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

class SnacksViewModel(application: Application) :
    AndroidViewModel(application) {

    val database = ItemsDatabase.getInstance(application)

    private var snacksList = listOf<Items>()

    private val _itemsSnacks = MutableLiveData<List<Items>>()
    val itemsSnacks: LiveData<List<Items>>
        get() = _itemsSnacks

    private fun getSnacksItemsFromDatabase() {
        viewModelScope.launch {
            launch(Dispatchers.IO) {

                _itemsSnacks.postValue(database.itemsDatabaseDao.getItems("snack"))

                snacksList = database.itemsDatabaseDao.getItems("snack")
                Log.d("get item from db", "" + snacksList)
            }


        }
    }

    init {
        getSnacksItemsFromDatabase()
    }


    //filter out snacks
    fun filterList(string: String): List<Items> {
        Log.d("FruitsViewModel", snacksList.toString())
        return filterItemsList(snacksList, string)
    }


    //sort list of snacks
    fun sortingSnacksList(): List<Items> {
        return snacksList.sortedBy { it.itemName }
    }
}