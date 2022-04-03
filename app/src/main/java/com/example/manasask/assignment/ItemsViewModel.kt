package com.example.manasask.assignment


import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.manasask.assignment.database.Items
import com.example.manasask.assignment.database.ItemsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ItemsViewModel(application: Application) :
    AndroidViewModel(application) {

    val database = ItemsDatabase.getInstance(application)

    private val _dataSaved = MutableLiveData<Boolean>()
    val dataSaved: LiveData<Boolean>
        get() = _dataSaved

    private val _searchText = MutableLiveData<String>()
    val seachText: LiveData<String>
        get() = _searchText

    private val _sortEnabled = MutableLiveData<Boolean>()
    val sortEnabled: LiveData<Boolean>
        get() = _sortEnabled


    init {
        _dataSaved.value = false
        _sortEnabled.value = false
    }

    private suspend fun insert(item: Items) {
        withContext(Dispatchers.IO) {
            database.itemsDatabaseDao.insert(item)
        }

    }

    fun addItem(item: Items) {
        viewModelScope.launch {

            // and insert it into the database.
            Log.d("Inserting item", item.toString())
            insert(item)
            _dataSaved.value = true
        }
    }

    fun onSaveFinished() {
        _dataSaved.value = false
    }


    fun searchText(text: String) {
        Log.d("search text item", text)
        _searchText.value = text
    }

    fun sortEnabled() {
        _sortEnabled.value = true
    }


}