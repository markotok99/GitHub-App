package com.aryanto.github.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.github.databinding.ActivityHomeBinding
import com.aryanto.github.utils.MyStatement
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val homeVM: HomeVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        binding.apply {
            homeAdapter = HomeAdapter(listOf())
            homeRecyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
            homeRecyclerView.adapter = homeAdapter
        }
    }

    private fun setViewModel() {
        binding.apply {
            homeVM.users.observe(this@HomeActivity) { result ->
                when (result) {
                    is MyStatement.Success -> {
                        homeProgressBar.visibility = View.GONE
                        result.data.let { homeAdapter.updateListItem(it) }

                    }

                    is MyStatement.Error -> {
                        homeProgressBar.visibility = View.GONE
                    }

                    is MyStatement.Loading -> {
                        homeProgressBar.visibility = View.VISIBLE
                    }
                }
            }

            lifecycleScope.launch {
                homeVM.getUsers()
            }

        }
    }

}