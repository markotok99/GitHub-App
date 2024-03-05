package com.aryanto.github.ui.fragments.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.github.R
import com.aryanto.github.data.model.Item
import com.aryanto.github.databinding.ItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class FollowingAdapter(
    private var following: List<Item>
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    class FollowingViewHolder(private val binding: ItemBinding) :
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return following.size
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(following[position])
    }

    fun updateListFollowing(newList: List<Item>) {

        val diffCalBack = diffCallBackUtils(following, newList)
        val diffResult = DiffUtil.calculateDiff(diffCalBack)

        following = newList
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