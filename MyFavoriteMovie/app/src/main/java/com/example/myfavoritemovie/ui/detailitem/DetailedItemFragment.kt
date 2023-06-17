package com.example.myfavoritemovie.ui.detailitem

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.myfavoritemovie.ui.ItemViewModel
import com.example.myfavoritemovie.data.utils.autoCleared
import com.example.myfavoritemovie.databinding.DetailItemLayoutBinding
import com.example.myfavoritemovie.R

class DetailedItemFragment(private val pressedLocation: IntArray) : DialogFragment(){

    private var binding: DetailItemLayoutBinding by autoCleared()
    private val viewModel : ItemViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        const val TAG = "DetailedItemDialog"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailItemLayoutBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.chosenItem.observe(viewLifecycleOwner) {
            binding.itemTitle.text = it.title
            binding.itemDesc.text = it.description
            binding.itemLength.text = "${it.length}${getString(R.string.length_minutes)}"
            binding.itemRating.text = "${getString(R.string.star)}".repeat(it.stars)
            binding.itemImage.setImageURI(Uri.parse(it.photo))
        }

        binding.itemCard.minimumWidth = (resources.displayMetrics.widthPixels * 0.85).toInt()
        binding.itemCard.minimumHeight = (resources.displayMetrics.heightPixels * 0.85).toInt()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart(){
        super.onStart()

        val window = dialog?.window
        window?.setBackgroundDrawableResource(R.color.transparent)
        val centerX = resources.displayMetrics.widthPixels / 2
        val centerY = resources.displayMetrics.heightPixels / 2
        val translationX = pressedLocation[0] - centerX
        val translationY = pressedLocation[1] - centerY
        val translateAnimatorX = ObjectAnimator.ofFloat(window?.decorView, "translationX", translationX.toFloat() ,0f)
        val translateAnimatorY = ObjectAnimator.ofFloat(window?.decorView, "translationY", translationY.toFloat()-40, 0f)
        val scaleAnimatorX = ObjectAnimator.ofFloat(window?.decorView, "scaleX", 0.54f, 1f)
        val scaleAnimatorY = ObjectAnimator.ofFloat(window?.decorView, "scaleY", 0.3f, 1f)
        val alphaAnimator = ObjectAnimator.ofFloat(window?.decorView, "alpha", 1f, 1f)

        val animatorSet = AnimatorSet().apply {
            playTogether(translateAnimatorX, translateAnimatorY, scaleAnimatorX, scaleAnimatorY, alphaAnimator)
            duration = 400L
        }
        animatorSet.start()
    }
}