package com.example.myfavoritemovie.ui.allitems

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myfavoritemovie.R
import com.example.myfavoritemovie.data.model.Item
import com.example.myfavoritemovie.databinding.ItemLayoutBinding

class ItemAdapter(private val items: List<Item>, private val callback: ItemListener, private val context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    interface ItemListener{
        fun onItemClicked(index:Int)
        fun onItemLongClicked(index:Int)
    }

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener, View.OnLongClickListener{

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        fun bind(item: Item){
            binding.movieTitle.text = item.title
            binding.movieTime.text = "${item.length}${context.getString(R.string.length_minutes)}"
            Glide.with(binding.root).load(item.photo).into(binding.moviePoster)
            binding.stars.text = "${context.getString(R.string.star)}".repeat(item.stars)
        }

        override fun onClick(p0: View?) {
            callback.onItemClicked(adapterPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            callback.onItemLongClicked(adapterPosition)
            return true
        }
    }

    fun itemAt(position : Int) = items[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount() = items.size
}