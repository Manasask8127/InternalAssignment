package com.example.manasask.assignment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.manasask.assignment.databinding.MainFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import androidx.lifecycle.ViewModelProvider
import com.example.manasask.assignment.common.ItemsAdapter
import com.example.manasask.assignment.database.Items
import com.example.manasask.assignment.fruits.FruitsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlin.collections.ArrayList


class MainFragment : Fragment() {


    private lateinit var binding: MainFragmentBinding
    private lateinit var itemsViewModel: ItemsViewModel

    private lateinit var fruitsViewModel: FruitsViewModel
    var list = ArrayList<Items>()
    var CATEGORY: String = "vegetable"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MainFragmentBinding.inflate(inflater, container, false)

        //val toolbar=binding.toolbar
        val viewPager = binding.viewPager
        viewPager.adapter = ItemsAdapter(this)

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getString(R.string.vegetables)
                }

                1 -> {
                    tab.text = getString(R.string.fruits)
                }
                else -> {
                    tab.text = getString(R.string.snacks)

                }
            }
        }.attach()


        val viewModelFactory = ItemsViewModelFactory(requireActivity().application)
        itemsViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        ).get(ItemsViewModel::class.java)

        fruitsViewModel = ViewModelProvider(
            this
        ).get(FruitsViewModel::class.java)

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                when (position) {
                    1 ->
                        CATEGORY = "fruit"
                    2 ->
                        CATEGORY = "snack"
                    else ->
                        CATEGORY = "vegetable"
                }
                Log.i("selected tab", position.toString() + " " + CATEGORY)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        (activity as AppCompatActivity).setSupportActionBar(binding.topAppBar)
        setHasOptionsMenu(true)


        //sorting applicable all three category at once
        binding.floatingActionButton.setOnClickListener {
            Log.d("MainFragment ", "fab button clicked")
            itemsViewModel.sortEnabled()

        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.top_up_menu, menu)
        val item = menu?.findItem(R.id.app_bar_search)


        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("MainFragment ", "onQuery submit called")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("MainFragment ", "onQuery change called")
                if (newText != null) {
                    itemsViewModel.searchText(newText)
                }


                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.app_bar_add -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToAddItemsFragment(
                        CATEGORY
                    )
                )
                return true
            }

            else ->
                return false
        }
        return super.onOptionsItemSelected(item)
    }
}