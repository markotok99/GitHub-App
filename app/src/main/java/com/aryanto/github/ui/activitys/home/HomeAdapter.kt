package com.aryanto.github.ui.activitys.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.github.R
import com.aryanto.github.data.model.Item
import com.aryanto.github.databinding.ItemBinding
import com.aryanto.github.ui.activitys.detail.DetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class HomeAdapter(
    private var users: List<Item>
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Item) {
            binding.apply {
                tvNamePerson.text = user.login

                Glide.with(root)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.person_24)
                    .error(R.drawable.broken_image_24)
                    .into(imageViewPerson)

                root.setOnClickListener {
                    val intent = Intent(root.context, DetailActivity::class.java)
                    intent.putExtra("username", user.login)
                    root.context.startActivity(intent)

                    Log.d("GH-HA List Item: ", user.login)
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

    fun updateListItem(newList: List<Item>) {

        val diffCalBack = diffCallBackUtils(users, newList)
        val diffResult = DiffUtil.calculateDiff(diffCalBack)

        users = newList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        fun diffCallBackUtils(
            oldList: List<Item>,
            newList: List<Item>
        ) = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition].id == newList[newItemPosition].id

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                oldList[oldItemPosition] == newList[newItemPosition]

        }
    }

}