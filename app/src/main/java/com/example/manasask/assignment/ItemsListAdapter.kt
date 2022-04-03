package com.example.manasask.assignment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.manasask.assignment.database.Items
import com.example.manasask.assignment.databinding.EachItemBinding
import java.lang.Character.toLowerCase
import java.util.*
import kotlin.collections.ArrayList

class ItemsListAdapter() : RecyclerView.Adapter<
        ItemsListAdapter.ItemsViewHolder>() {

    private var unfilteredList = listOf<Items>()

    var data = listOf<Items>()
        set(value) {
            unfilteredList = value
            field = value
            notifyDataSetChanged()
        }


    //var itemsList=ArrayList<Items>()

//    fun submitItems(list: List<Items>?){
////        val diffcalback=ItemsDiffCallback()
////        val diffResult=DiffUtil.calculateDiff(diffcalback)
//        if (list != null) {
//            data=list
//        }
//        diffResult.dispatchUpdatesTo(this)
//    }


    class ItemsViewHolder(val binding: EachItemBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ItemsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EachItemBinding.inflate(layoutInflater, parent, false)
                return ItemsViewHolder(binding)
            }
        }

        fun bind(eachItems: Items) {
            binding.item = eachItems
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder.from(parent)

    }


    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size

}


//to avoid notifydatasetchanged() some error while implementing , need to check
class ItemsDiffCallback : DiffUtil.ItemCallback<Items>() {
    override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem == newItem
    }
}