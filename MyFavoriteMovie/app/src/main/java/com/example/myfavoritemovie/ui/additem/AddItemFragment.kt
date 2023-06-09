package com.example.myfavoritemovie.ui.additem

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.data.model.Item
import com.example.myfavoritemovie.ui.ItemViewModel
import com.example.myfavoritemovie.data.utils.autoCleared
import com.example.myfavoritemovie.databinding.AddItemPageFragmentBinding
import com.example.myfavoritemovie.ui.ItemUtils

class AddItemFragment : Fragment() {

    private var binding : AddItemPageFragmentBinding by autoCleared()
    private var imageUri : Uri? = null
    private var numberOfStars : Int = 0
    private val viewModel : ItemViewModel by activityViewModels()

    //let the user pick an image from the phone files
    private val pickItemLauncher : ActivityResultLauncher<Array<String>> =
    registerForActivityResult(ActivityResultContracts.OpenDocument()){
        uri: Uri? ->
        if(uri != null){
            Glide.with(requireContext()).load(uri).circleCrop()
                .into(binding.pickImage)
            requireActivity().contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageUri = uri
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddItemPageFragmentBinding.inflate(inflater, container, false)

        //set minimum and maximum for the movie length picker
        binding.movieLengthHours.minValue = 0
        binding.movieLengthHours.maxValue = 20
        binding.movieLengthMinutes.minValue = 0
        binding.movieLengthMinutes.maxValue = 60

        //place the default image
        Glide.with(requireContext()).load(ContextCompat.getDrawable(requireContext(),R.drawable.movie_picture_place_holder)).circleCrop()
            .into(binding.pickImage)

        //create and add an item to the view model if the user provided the needed inputs
        binding.addButton.setOnClickListener {
            if(!ItemUtils.validateInput(numberOfStars, binding.movieTitle.toString(), binding.movieDesc.toString(),
                binding.movieLengthMinutes.value, binding.movieLengthHours.value, imageUri)){
                Toast.makeText(requireContext(),getString(R.string.invalid_input_message), Toast.LENGTH_LONG).show()
            }
            else {
                val item = Item(
                    binding.movieTitle.text.toString(),
                    binding.movieDesc.text.toString(),
                    binding.movieLengthHours.value * 60 + binding.movieLengthMinutes.value,
                    imageUri.toString(),
                    numberOfStars
                )
                viewModel.addItem(item)

                findNavController().navigate(R.id.action_addItemFragment_to_mainPageFragment)
            }
        }

        //if the first star is clicked, make him full
        binding.firstStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, false)
            ItemUtils.changeStar(binding.thirdStar, false)
            ItemUtils.changeStar(binding.fourthStar, false)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 1
        }

        //if the second star is clicked, make him and the 'smaller' stars full
        binding.secondStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, false)
            ItemUtils.changeStar(binding.fourthStar, false)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 2
        }

        //if the third star is clicked, make him and the 'smaller' stars full
        binding.thirdStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, true)
            ItemUtils.changeStar(binding.fourthStar, false)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 3
        }

        //if the forth star is clicked, make him and the 'smaller' stars full
        binding.fourthStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, true)
            ItemUtils.changeStar(binding.fourthStar, true)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 4
        }

        //if the fifth star is clicked, make him and the 'smaller' stars full
        binding.fifthStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, true)
            ItemUtils.changeStar(binding.fourthStar, true)
            ItemUtils.changeStar(binding.fifthStar, true)
            numberOfStars = 5
        }

        //launch the pickItemLauncher to pick an image
        binding.pickImageButton.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
        }

        return binding.root
    }
}