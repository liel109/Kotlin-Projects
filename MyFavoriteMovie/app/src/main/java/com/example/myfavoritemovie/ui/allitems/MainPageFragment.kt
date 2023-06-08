package com.example.myfavoritemovie.ui.allitems

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.ui.ItemViewModel
import com.example.myfavoritemovie.data.utils.autoCleared
import com.example.myfavoritemovie.databinding.MainPageFragmentBinding

class MainPageFragment : Fragment() {

    private var binding: MainPageFragmentBinding by autoCleared()

    private val viewModel : ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MainPageFragmentBinding.inflate(inflater, container, false)

        binding.addButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainPageFragment_to_addItemFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.items?.observe(viewLifecycleOwner){
            binding.recycleView.adapter = ItemAdapter(it, object : ItemAdapter.ItemListener {
                override fun onItemClicked(index: Int) {
                    viewModel.setItem((binding.recycleView.adapter as ItemAdapter).itemAt(index))
                    //findNavController().navigate(R.id.action_mainPageFragment_to_detailedItemFragment)
                    val dialogBuilder = AlertDialog.Builder(context)
                    val dialogView : View = layoutInflater.inflate(R.layout.detail_item_layout, null)

                    val viewModel : ItemViewModel by activityViewModels()

                    viewModel.chosenItem.observe(viewLifecycleOwner) {
                        dialogView.findViewById<TextView>(R.id.item_title).text = it.title
                        dialogView.findViewById<TextView>(R.id.item_length).text = "${it.length}${getString(R.string.length_minutes)}"
                        dialogView.findViewById<TextView>(R.id.item_desc).text = it.description
                        dialogView.findViewById<TextView>(R.id.item_rating).text = "★".repeat(it.stars)
                        Glide.with(requireContext()).load(it.photo)
                            .into(dialogView.findViewById<ImageView>(R.id.item_image))
                    }
                    dialogBuilder.setView(dialogView)
                    val alertDialog = dialogBuilder.create()
                    val window = alertDialog.window

                    window?.setBackgroundDrawableResource(R.color.transparent)
                    alertDialog.show()

                    val centerX = resources.displayMetrics.widthPixels / 2
                    val centerY = resources.displayMetrics.heightPixels / 2
                    val location = IntArray(2)
                    val itemView = binding.recycleView[index]
                    itemView.getLocationOnScreen(location)
                    val currentX = location[0] + (itemView.width / 2)
                    val currentY = location[1] + (itemView.height / 2)
                    val translationX = currentX - centerX
                    val translationY = currentY - centerY
                    val translateAnimatorX = ObjectAnimator.ofFloat(window?.decorView, "translationX", translationX.toFloat() ,0f)
                    val translateAnimatorY = ObjectAnimator.ofFloat(window?.decorView, "translationY", translationY.toFloat(), 0f)
                    val scaleAnimatorX = ObjectAnimator.ofFloat(window?.decorView, "scaleX", 0.5f, 1f)
                    val scaleAnimatorY = ObjectAnimator.ofFloat(window?.decorView, "scaleY", 0.35f, 1f)
                    val alphaAnimator = ObjectAnimator.ofFloat(window?.decorView, "alpha", 1f, 1f)

                    val animatorSet = AnimatorSet().apply {
                        playTogether(translateAnimatorX, translateAnimatorY, scaleAnimatorX, scaleAnimatorY, alphaAnimator)
                        duration = 500L
                    }
                    animatorSet.start()
                }

                override fun onItemLongClicked(index: Int) {
                    viewModel.setItem((binding.recycleView.adapter as ItemAdapter).itemAt(index))
                    findNavController().navigate(R.id.action_mainPageFragment_to_editItemFragment)
                }
            }, requireContext())
        }

        binding.clearButton.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(getString(R.string.confirmation))
            builder.setMessage(getString(R.string.confirmation_dialog))

            builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteAllItems()
            }

            builder.setNegativeButton(getString(R.string.no)) { _, _ ->

            }

            val dialog = builder.create()
            dialog.show()
        }

        binding.recycleView.layoutManager = GridLayoutManager(requireContext(),2)

        ItemTouchHelper(object : ItemTouchHelper.Callback(){

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            )= makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteItem((binding.recycleView.adapter as ItemAdapter)
                    .itemAt(viewHolder.adapterPosition))
                binding.recycleView.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.recycleView)
    }
}