package com.example.myfavoritemovie.ui.allitems

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritemovie.ui.ItemViewModel
import com.example.myfavoritemovie.R
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
                    findNavController().navigate(R.id.action_mainPageFragment_to_detailedItemFragment)
                }

                override fun onItemLongClicked(index: Int) {
                    Toast.makeText(requireContext(),"edit",Toast.LENGTH_LONG).show()
                }
            })
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
            )= makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

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