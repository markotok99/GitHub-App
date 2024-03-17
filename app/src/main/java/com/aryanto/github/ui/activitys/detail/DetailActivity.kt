package com.aryanto.github.ui.activitys.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.aryanto.github.R
import com.aryanto.github.data.model.ItemDetail
import com.aryanto.github.databinding.ActivityDetailBinding
import com.aryanto.github.ui.fragments.followers.FollowerFragment
import com.aryanto.github.ui.fragments.following.FollowingFragment
import com.aryanto.github.utils.MyStatement
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var detailAdapter: DetailAdapter

    private val detailVM: DetailVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        if (username != null) {
            setViewPager(username)
            if (savedInstanceState == null) {
                detailVM.getDetailUser(username)
            }
            setViewModel()
        }


    }

    private fun setViewModel() {

        binding.apply {
            detailVM.detailUser.observe(this@DetailActivity) { result ->
                when (result) {
                    is MyStatement.Success -> {
                        detailUserProgressBar.visibility = View.GONE
                        val user = result.data.first()
                        bindDetailUser(user)
                    }

                    is MyStatement.Error -> {
                        detailUserProgressBar.visibility = View.GONE
                    }

                    is MyStatement.Loading -> {
                        detailUserProgressBar.visibility = View.VISIBLE
                    }
                }
            }

        }
    }

    private fun setViewPager(username: String) {
        binding.apply {
            tabLayout = tabLayoutUserDetail
            viewPager = viewPagerUserDetail

            detailAdapter = DetailAdapter(this@DetailActivity, listOf())
            viewPager.adapter = detailAdapter

            detailAdapter.addFragment(FollowerFragment.newFragment(username))
            detailAdapter.addFragment(FollowingFragment.newFragment(username))

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Followers"
                    }

                    1 -> {
                        tab.text = "Following"
                    }
                }

            }.attach()
        }
    }

    private fun bindDetailUser(user: ItemDetail) {
        binding.apply {
            tvUserDetailName.text = user.name?.toString()
            tvUserDetailUsername.text = user.login
            tvUserDetailFollowers.text = user.followers.toString()
            tvUserDetailFollowing.text = user.following.toString()

            Glide.with(root)
                .load(user.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.person_24)
                .error(R.drawable.broken_image_24)
                .into(ivUserDetail)
        }
        Log.d("GH-DA BDU", "UI Updated with user: $user")
    }
}