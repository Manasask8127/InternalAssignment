package com.example.manasask.assignment.vegetables

import android.os.Bundle
import android.util.Log
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
import com.example.manasask.assignment.databinding.ItemsFragmentBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VegetablesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VegetablesFragment : Fragment() {

    private lateinit var viewModel: VegetablesViewModel
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

        viewModel = ViewModelProvider(this).get(VegetablesViewModel::class.java)


        val manager = GridLayoutManager(activity, 3)


        binding.itemsRecyclerList.layoutManager = manager


        val adapter = ItemsListAdapter()
        binding.itemsRecyclerList.adapter = adapter

        viewModel.itemsVegetables.observe(viewLifecycleOwner, Observer { itemlist ->
            //Log.d("vegetabl list", itemlist.toString())
            adapter.data = itemlist

        })

        itemsViewModel.seachText.observe(viewLifecycleOwner, Observer {
            Log.d("searchtext in fragment", it)
            if (it != null) {
                Log.d("Manasa search", it.toString())
                adapter.data = viewModel.filterList(it)
                //viewModel.sortDisabled()
            }
        })

        itemsViewModel.sortEnabled.observe(viewLifecycleOwner, Observer {
            adapter.data = viewModel.sortingVegetableList()
        })


        return binding.root
    }

}