package com.aryanto.github.ui.fragments.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.github.R
import com.aryanto.github.databinding.FragmentFollowingBinding
import com.aryanto.github.ui.fragments.followers.FollowerFragment
import com.aryanto.github.ui.fragments.followers.FollowersAdapter
import com.aryanto.github.utils.MyStatement
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followingAdapter: FollowingAdapter
    private val followingVM: FollowingVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        binding.apply {
            followingAdapter = FollowingAdapter(listOf())
            rvFollowers.adapter = followingAdapter
            rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setViewModel() {
        binding.apply {
            followingVM.following.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is MyStatement.Success -> {
                        progressBar.visibility = View.GONE
                        if (result.data.isEmpty()) {
                            tvEmptyMessage.visibility = View.VISIBLE
                            rvFollowers.visibility = View.GONE
                        } else {
                            tvEmptyMessage.visibility = View.GONE
                            rvFollowers.visibility = View.VISIBLE
                            followingAdapter.updateListFollowing(result.data)
                        }
                    }

                    is MyStatement.Error -> {
                        progressBar.visibility = View.GONE
                    }

                    is MyStatement.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }

            arguments?.getString("username")?.let {
                followingVM.fetchFollowing(it)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

    companion object{
        fun newFragment(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val args = Bundle()
            args.putString("username", username)
            fragment.arguments = args
            return fragment
        }
    }

}