package com.example.manasask.assignment.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.manasask.assignment.fruits.FruitFragment
import com.example.manasask.assignment.snacks.SnacksFragment
import com.example.manasask.assignment.vegetables.VegetablesFragment

class ItemsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> VegetablesFragment()
            1 -> FruitFragment()
            else
            -> SnacksFragment()
        }
    }


}