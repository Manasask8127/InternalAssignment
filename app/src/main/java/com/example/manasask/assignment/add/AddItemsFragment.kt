package com.example.manasask.assignment.add

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.manasask.assignment.ItemsViewModel
import com.example.manasask.assignment.ItemsViewModelFactory
import com.example.manasask.assignment.R
import com.example.manasask.assignment.database.Items
import com.example.manasask.assignment.database.ItemsDatabase
import com.example.manasask.assignment.databinding.AddItemsFragmentBinding
import com.google.android.material.appbar.AppBarLayout
import android.widget.Toast

import android.app.Activity.RESULT_CANCELED

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import java.lang.Exception
import java.lang.NumberFormatException
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.Observer
import java.io.InputStream

//adding items to list
class AddItemsFragment : Fragment() {

    private val IMAGE = "image";
    private val REQUEST_CODE = 1
    lateinit var imageUri: Uri
    private lateinit var viewModel: ItemsViewModel

    private lateinit var binding: AddItemsFragmentBinding
    var imagePath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("AddItemsFragment", "called")
        binding = AddItemsFragmentBinding.inflate(
            inflater, container, false
        )


        val viewModelFactory = ItemsViewModelFactory(requireActivity().application)

        viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(ItemsViewModel::class.java)
        val args = AddItemsFragmentArgs.fromBundle(requireArguments())


        val toolbar = binding.addTopupbar

        toolbar.setNavigationOnClickListener {
            navigateTo()
        }

        if (savedInstanceState != null) {
            imagePath = savedInstanceState.getString(IMAGE, "")
            binding.itemImage.setImageURI(Uri.parse(savedInstanceState.getString(IMAGE)))
        }


        binding.itemImage.setOnClickListener {
            selectFromGalley()
        }

        binding.buttonSave.setOnClickListener {
            val itemName = binding.itemName.text
            val itemPath = imagePath
            //safe args for category
            val itemCategory = args.category

            if (itemName.isEmpty()) {
                Toast.makeText(activity, "Please enter name", Toast.LENGTH_SHORT).show()
            } else {

                Log.d(
                    "inserting item in frag",
                    " " + itemName + " " + itemPath + " " + itemCategory
                )
                viewModel.addItem(
                    Items(
                        itemName = itemName.toString(),
                        itemImagePath = itemPath,
                        itemCategory = itemCategory
                    )
                )
            }


        }

        viewModel.dataSaved.observe(viewLifecycleOwner, Observer { isInserted ->
            if (isInserted) {
                viewModel.onSaveFinished()
                navigateTo()
            }
        })






        return binding.root
    }

    private fun navigateTo() {
        findNavController().navigate(AddItemsFragmentDirections.actionAddItemsFragmentToMainFragment())
    }

    //select an image from gallery
    private fun selectFromGalley() {
        // if(askForPermissions())
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)

    }

    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_CODE && null != data) {
                    onSelectFromGalleryResult(data)
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(
                    context,
                    "Image not selected", Toast.LENGTH_SHORT
                )
                    .show()
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onSelectFromGalleryResult(data: Intent) {

        try {
            imageUri = data.data!!
            imagePath = imageUri.toString()
            binding.itemImage.setImageURI(imageUri)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(IMAGE, imagePath)

        Log.i("AddItemsFragment", "onSaveInstanceState called")

    }


}




