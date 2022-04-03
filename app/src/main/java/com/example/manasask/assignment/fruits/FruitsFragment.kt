package com.example.manasask.assignment.fruits

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.manasask.assignment.ItemsListAdapter
import com.example.manasask.assignment.ItemsViewModel
import com.example.manasask.assignment.ItemsViewModelFactory
import com.example.manasask.assignment.database.ItemsDatabase
import com.example.manasask.assignment.databinding.ItemsFragmentBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FruitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FruitFragment : Fragment() {


    private lateinit var viewModel: FruitsViewModel
    private lateinit var itemsViewModel: ItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = ItemsFragmentBinding.inflate(inflater, container, false)


        val viewModelFactory = ItemsViewModelFactory(requireActivity().application)

        itemsViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(ItemsViewModel::class.java)

        viewModel = ViewModelProvider(
            this
        ).get(FruitsViewModel::class.java)

        //binding.lifecycleOwner=this


        val manager = GridLayoutManager(activity, 3)


        binding.itemsRecyclerList.layoutManager = manager

        setHasOptionsMenu(true)

        val adapter = ItemsListAdapter()
        binding.itemsRecyclerList.adapter = adapter

        viewModel.itemsFruits.observe(viewLifecycleOwner, Observer { itemlist ->
            adapter.data = itemlist

        })

        itemsViewModel.seachText.observe(viewLifecycleOwner, Observer {
            adapter.data = viewModel.filterList(it)
        })

        itemsViewModel.sortEnabled.observe(viewLifecycleOwner, Observer {
            adapter.data = viewModel.sortingFruitsList()
        })



        return binding.root
    }
}