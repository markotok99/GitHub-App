package com.aryanto.github.ui.fragments.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.github.databinding.FragmentFollowerBinding
import com.aryanto.github.utils.MyStatement
import org.koin.androidx.viewmodel.ext.android.viewModel


class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followersAdapter: FollowersAdapter
    private val followersVM: FollowersVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setViewModel()
    }

    private fun setAdapter() {
        binding.apply {
            followersAdapter = FollowersAdapter(listOf())
            rvFollowers.adapter = followersAdapter
            rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setViewModel() {
        binding.apply {
            followersVM.followers.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is MyStatement.Success -> {
                        progressBar.visibility = View.GONE
                        if (result.data.isEmpty()) {
                            tvEmptyMessage.visibility = View.VISIBLE
                            rvFollowers.visibility = View.GONE
                        } else {
                            tvEmptyMessage.visibility = View.GONE
                            rvFollowers.visibility = View.VISIBLE
                            followersAdapter.updateListFollowers(result.data)
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
                followersVM.fetchFollowers(it)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding
    }

    companion object {
        fun newFragment(username: String): FollowerFragment {
            val fragment = FollowerFragment()
            val args = Bundle()
            args.putString("username", username)
            fragment.arguments = args
            return fragment
        }
    }
}