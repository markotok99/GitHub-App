package com.aryanto.github.ui.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.github.data.model.Item
import com.aryanto.github.databinding.ItemBinding
import com.bumptech.glide.Glide

class HomeAdapter(
    private var users: List<Item>
): RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(private val binding: ItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Item){
            binding.apply {
                tvNamePerson.text = user.login

                Glide.with(itemView)
                    .load(user.avatar_url)
                    .into(imageViewPerson)

                itemView.setOnClickListener {

                    Log.d("GH-HA List Item: ", "$user")
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun updateListItem(newList: List<Item>){
        val diffResult = DiffUtil.calculateDiff(diffCallBack(users, newList))
        users = newList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object{
        fun diffCallBack(
            oldList: List<Item>,
            newList: List<Item>
        ) = object : DiffUtil.Callback(){
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].login == newList[newItemPosition].login

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition] == newList[newItemPosition]

        }
    }

}