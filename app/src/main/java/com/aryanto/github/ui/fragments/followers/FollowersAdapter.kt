package com.aryanto.github.ui.fragments.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.github.R
import com.aryanto.github.data.model.Item
import com.aryanto.github.databinding.ItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FollowersAdapter(
    private var followers: List<Item>
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    class FollowersViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                tvNamePerson.text = item.login

                Glide.with(root)
                    .load(item.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.person_24)
                    .error(R.drawable.broken_image_24)
                    .into(imageViewPerson)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return followers.size
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(followers[position])
    }

    fun updateListFollowers(newList: List<Item>) {

        val diffCalBack = diffCallBackUtils(followers, newList)
        val diffResult = DiffUtil.calculateDiff(diffCalBack)

        followers = newList
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