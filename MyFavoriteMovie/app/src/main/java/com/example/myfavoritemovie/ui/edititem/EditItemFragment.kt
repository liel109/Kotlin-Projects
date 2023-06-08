package com.example.myfavoritemovie.ui.edititem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.data.utils.autoCleared
import com.example.myfavoritemovie.databinding.EditItemFragmentBinding
import com.example.myfavoritemovie.ui.ItemViewModel
import com.example.myfavoritemovie.ui.ItemUtils

class EditItemFragment : Fragment() {
    private val ANIMATION_DURATION = 75L
    private var binding: EditItemFragmentBinding by autoCleared()
    private val viewModel : ItemViewModel by activityViewModels()
    private var imageUri : Uri? = null
    private var numberOfStars : Int = 0


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
        binding = EditItemFragmentBinding.inflate(layoutInflater,container,false)

        binding.movieLengthHours.minValue = 0
        binding.movieLengthHours.maxValue = 20
        binding.movieLengthMinutes.minValue = 0
        binding.movieLengthMinutes.maxValue = 60

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.chosenItem.observe(viewLifecycleOwner) {
            imageUri = Uri.parse(it.photo)
            binding.movieTitle.setText(it.title)
            binding.movieDesc.setText(it.description)
            binding.movieLengthMinutes.value = it.length%60
            binding.movieLengthHours.value = it.length/60
            numberOfStars = it.stars
            initiateStars(numberOfStars)
            Glide.with(requireContext()).load(it.photo).circleCrop()
                .into(binding.pickImage)
        }

        binding.changeButton.setOnClickListener {
            viewModel.chosenItem.observe(viewLifecycleOwner){
                if(!ItemUtils.validateInput(numberOfStars, binding.movieTitle.text.toString(), binding.movieDesc.text.toString(),
                        binding.movieLengthMinutes.value, binding.movieLengthHours.value, imageUri)) {
                    Toast.makeText(requireContext(),getString(R.string.invalid_input_message), Toast.LENGTH_LONG).show()
                }
                else {
                    it.title = binding.movieTitle.text.toString()
                    it.description = binding.movieTitle.text.toString()
                    it.length = binding.movieLengthHours.value * 60 + binding.movieLengthMinutes.value
                    it.stars = numberOfStars
                    it.photo = imageUri.toString()
                    viewModel.updateItem(it)
                    findNavController().navigate(R.id.action_editItemFragment_to_mainPageFragment)
                }
            }
        }


        binding.changeImageButton.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
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


        super.onViewCreated(view, savedInstanceState)
    }

    private fun initiateStars(numberOfStars: Int){
        binding.firstStar.setImageResource(R.drawable.ic_full_star)
        if(numberOfStars > 1){
            binding.secondStar.setImageResource(R.drawable.ic_full_star)
        }
        if(numberOfStars > 2){
            binding.thirdStar.setImageResource(R.drawable.ic_full_star)
        }
        if(numberOfStars > 3){
            binding.fourthStar.setImageResource(R.drawable.ic_full_star)
        }
        if(numberOfStars > 4){
            binding.fifthStar.setImageResource(R.drawable.ic_full_star)
        }
    }
}


