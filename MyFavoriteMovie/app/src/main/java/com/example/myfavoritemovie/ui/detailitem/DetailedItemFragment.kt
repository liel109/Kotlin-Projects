package com.example.myfavoritemovie.ui.detailitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.myfavoritemovie.ui.ItemViewModel
import com.example.myfavoritemovie.data.utils.autoCleared
import com.example.myfavoritemovie.databinding.DetailItemLayoutBinding

class DetailedItemFragment : Fragment(){

    private var binding: DetailItemLayoutBinding by autoCleared()
    private val viewModel : ItemViewModel by activityViewModels()

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
            binding.itemDesc.text = it.length.toString()
            Glide.with(requireContext()).load(it.photo).circleCrop()
                .into(binding.itemImage)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}