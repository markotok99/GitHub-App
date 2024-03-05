package com.aryanto.github.ui.activitys.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aryanto.github.data.model.ItemDetail

class DetailAdapter(
    activity: AppCompatActivity,
    var userData: List<ItemDetail>
) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]

}