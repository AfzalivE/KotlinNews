package com.afzaln.kotlinnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.afzaln.kotlinnews.data.models.PostData
import com.afzaln.kotlinnews.data.models.Thing
import com.afzaln.kotlinnews.databinding.FragmentPostListBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PostListFragment : Fragment() {

    private val viewModel: PostListViewModel by viewModels { PostListViewModelFactory() }
    private lateinit var binding: FragmentPostListBinding
    private lateinit var postItemAdapter: PostItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postItemAdapter = PostItemAdapter()
        binding.postList.layoutManager = LinearLayoutManager(context)
        binding.postList.adapter = postItemAdapter

        viewModel.fetchPosts().observe(viewLifecycleOwner, {
            updateList(it.data.children)
        })
    }

    private fun updateList(posts: List<Thing<PostData>>) {
        Timber.d("Loaded")
        postItemAdapter.postList = posts
    }
}
