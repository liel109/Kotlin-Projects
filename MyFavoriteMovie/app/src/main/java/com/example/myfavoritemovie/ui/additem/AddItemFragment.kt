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

    private val ANIMATION_DURATION = 75L

    private var binding : AddItemPageFragmentBinding by autoCleared()

    private var imageUri : Uri? = null

    private var numberOfStars : Int = 0

    private val viewModel : ItemViewModel by activityViewModels()

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

        binding.movieLengthHours.minValue = 0
        binding.movieLengthHours.maxValue = 20
        binding.movieLengthMinutes.minValue = 0
        binding.movieLengthMinutes.maxValue = 60

        Glide.with(requireContext()).load(ContextCompat.getDrawable(requireContext(),R.drawable.movie_picture_place_holder)).circleCrop()
            .into(binding.pickImage)

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

        binding.firstStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, false)
            ItemUtils.changeStar(binding.thirdStar, false)
            ItemUtils.changeStar(binding.fourthStar, false)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 1
        }

        binding.secondStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, false)
            ItemUtils.changeStar(binding.fourthStar, false)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 2
        }

        binding.thirdStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, true)
            ItemUtils.changeStar(binding.fourthStar, false)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 3
        }

        binding.fourthStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, true)
            ItemUtils.changeStar(binding.fourthStar, true)
            ItemUtils.changeStar(binding.fifthStar, false)
            numberOfStars = 4
        }

        binding.fifthStar.setOnClickListener {
            ItemUtils.changeStar(binding.firstStar, true)
            ItemUtils.changeStar(binding.secondStar, true)
            ItemUtils.changeStar(binding.thirdStar, true)
            ItemUtils.changeStar(binding.fourthStar, true)
            ItemUtils.changeStar(binding.fifthStar, true)
            numberOfStars = 5
        }

        binding.pickImageButton.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
        }

        return binding.root
    }
}