package com.example.myfavoritemovie.ui.edititem

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

class EditItemFragment : Fragment() {
    private val ANIMATION_DURATION = 75L
    private var binding: EditItemFragmentBinding by autoCleared()
    private val viewModel : ItemViewModel by activityViewModels()
    private var imageUri : String? = null
    private var numberOfStars : Int = 0


    private val pickItemLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()){
                uri: Uri? ->
            if(uri != null){
                binding.pickImage.setImageURI(uri)
                requireActivity().contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                imageUri = uri.toString()
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
            imageUri = it.photo
            binding.movieTitle.setText(it.title)
            binding.movieDesc.setText(it.description)
            binding.movieLengthMinutes.value = it.length%60
            binding.movieLengthHours.value = it.length/60
            initiateStars(it.stars)
            Glide.with(requireContext()).load(it.photo).circleCrop()
                .into(binding.pickImage)
        }

        binding.changeButton.setOnClickListener {
            viewModel.chosenItem.observe(viewLifecycleOwner){
                it.title = binding.movieTitle.text.toString()
                it.length = binding.movieLengthHours.value * 60 + binding.movieLengthMinutes.value
                it.stars = numberOfStars
                it.photo = imageUri
                viewModel.updateItem(it)
            }
            findNavController().navigate(R.id.action_editItemFragment_to_mainPageFragment)
        }


        binding.changeImageButton.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
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

    private fun changeStar(star: ImageView, full : Boolean){
        if(full){
            star.setImageResource(R.drawable.ic_full_star)

            val scaleX = ObjectAnimator.ofFloat(star,"scaleX",1f, 1.2f).setDuration(ANIMATION_DURATION)
            val scaleY = ObjectAnimator.ofFloat(star,"scaleY",1f,1.2f).setDuration(ANIMATION_DURATION)
            scaleX.repeatCount = 1
            scaleX.repeatMode = ObjectAnimator.REVERSE
            scaleY.repeatCount = 1
            scaleY.repeatMode = ObjectAnimator.REVERSE

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleX,scaleY)
            animatorSet.start()

        }
        else{
            star.setImageResource(R.drawable.ic_empty_star)
        }
    }
}


