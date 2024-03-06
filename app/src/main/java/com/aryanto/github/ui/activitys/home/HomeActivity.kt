package com.aryanto.github.ui.activitys.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.github.databinding.ActivityHomeBinding
import com.aryanto.github.utils.MyStatement
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val homeVM: HomeVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            homeVM.getUsers()
        }

        setAdapter()
        setViewModel()
        setSearchComponent()

    }

    private fun setAdapter() {
        binding.apply {
            homeAdapter = HomeAdapter(listOf())
            homeRecyclerView.adapter = homeAdapter
            homeRecyclerView.layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun setViewModel() {
        binding.apply {
            homeVM.users.observe(this@HomeActivity) { result ->
                when (result) {
                    is MyStatement.Success -> {
                        homeProgressBar.visibility = View.GONE
                        homeAdapter.updateListItem(result.data)
                    }

                    is MyStatement.Error -> {
                        homeProgressBar.visibility = View.GONE
                    }

                    is MyStatement.Loading -> {
                        homeProgressBar.visibility = View.VISIBLE
                    }
                }
            }

        }
    }

    private fun setSearchComponent() {
        binding.apply {
            materialSearchView.setupWithSearchBar(materialSearchBar)
            materialSearchView.editText.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    val searchText = textView.text.toString()
                    handleSearch(searchText)
                    materialSearchView.hide()
                    true
                }else{
                    false
                }
            }
        }
    }

    private fun handleSearch(query: String) {
        query.let {
            binding.apply {
                homeVM.searchUser(query)
            }
        }
    }

}