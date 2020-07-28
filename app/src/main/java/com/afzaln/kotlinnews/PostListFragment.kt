package com.afzaln.kotlinnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.afzaln.kotlinnews.databinding.FragmentPostListBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PostListFragment : Fragment() {

    private val viewModel: PostListViewModel by viewModels { PostListViewModelFactory() }

    private lateinit var binding: FragmentPostListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchPosts()
        viewModel.fetchPosts().observe(viewLifecycleOwner, {
            Timber.d(it.kind)
        })
    }
}
