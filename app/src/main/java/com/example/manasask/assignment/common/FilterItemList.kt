package com.example.manasask.assignment

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.manasask.assignment.database.Items
import java.util.*

fun filterItemsList(unfilteredList: List<Items>, query: String): List<Items> {
    val list = mutableListOf<Items>()
    if (query.isEmpty()) {
        list.addAll(unfilteredList)
    } else {
        list.addAll(unfilteredList.filter { item ->
            ((
                    (item.itemName)
                        .lowercase(Locale.getDefault())
                        .contains(query.lowercase(Locale.getDefault())) ||
                            (item.itemName.lowercase(Locale.getDefault()).contains(
                                query.lowercase(
                                    Locale.getDefault()
                                )
                            ))))
        })
    }
    return list
}