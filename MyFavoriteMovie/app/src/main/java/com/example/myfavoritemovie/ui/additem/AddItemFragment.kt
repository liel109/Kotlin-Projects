package com.example.myfavoritemovie.ui.additem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myfavoritemovie.data.model.Item
import com.example.myfavoritemovie.ui.ItemViewModel
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.data.utils.autoCleared
import com.example.myfavoritemovie.databinding.AddItemPageFragmentBinding

class AddItemFragment : Fragment() {

    private var binding : AddItemPageFragmentBinding by autoCleared()

    private var imageUri : Uri? = null

    private var numberOfStars : Int = 0

    private val viewModel : ItemViewModel by activityViewModels()


    val pickItemLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()){
            binding.pickImage.setImageURI(it)
            requireActivity().contentResolver.takePersistableUriPermission(it!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imageUri = it
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddItemPageFragmentBinding.inflate(inflater, container, false)

        binding.addButton.setOnClickListener {
            if(numberOfStars == 0){
                Toast.makeText(requireContext(),"Pick a raiting for the movie!", Toast.LENGTH_LONG).show()
            }
            else {
                val item = Item(
                    binding.movieTitle.text.toString(),
                    binding.movieLength.text.toString(),
                    imageUri.toString(),
                    numberOfStars
                )
                viewModel.addItem(item)

                findNavController().navigate(R.id.action_addItemFragment_to_mainPageFragment)
            }
        }

        binding.firstStar.setOnClickListener {
            changeStar(binding.firstStar, true)
            changeStar(binding.secondStar, false)
            changeStar(binding.thirdStar, false)
            changeStar(binding.fourthStar, false)
            changeStar(binding.fifthStar, false)
            numberOfStars = 1
        }

        binding.secondStar.setOnClickListener {
            changeStar(binding.firstStar, true)
            changeStar(binding.secondStar, true)
            changeStar(binding.thirdStar, false)
            changeStar(binding.fourthStar, false)
            changeStar(binding.fifthStar, false)
            numberOfStars = 2
        }

        binding.thirdStar.setOnClickListener {
            changeStar(binding.firstStar, true)
            changeStar(binding.secondStar, true)
            changeStar(binding.thirdStar, true)
            changeStar(binding.fourthStar, false)
            changeStar(binding.fifthStar, false)
            numberOfStars = 3
        }

        binding.fourthStar.setOnClickListener {
            changeStar(binding.firstStar, true)
            changeStar(binding.secondStar, true)
            changeStar(binding.thirdStar, true)
            changeStar(binding.fourthStar, true)
            changeStar(binding.fifthStar, false)
            numberOfStars = 4
        }

        binding.fifthStar.setOnClickListener {
            changeStar(binding.firstStar, true)
            changeStar(binding.secondStar, true)
            changeStar(binding.thirdStar, true)
            changeStar(binding.fourthStar, true)
            changeStar(binding.fifthStar, true)
            numberOfStars = 5
        }

        binding.pickImageButton.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
        }

        return binding.root
    }



    private fun changeStar(star: ImageView, full : Boolean){
        if(full){
            star.setImageResource(R.drawable.ic_full_star)
        }
        else{
            star.setImageResource(R.drawable.ic_empty_star)
        }
    }

}