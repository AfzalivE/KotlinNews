package com.afzaln.kotlinnews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
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
        postItemAdapter = PostItemAdapter(::onItemClick)
        // To improve UX if lots of images are showing
        binding.postList.setItemViewCacheSize(20)
        binding.postList.adapter = postItemAdapter

        viewModel.fetchPosts().observe(viewLifecycleOwner, {
            updateList(it.data.children)
        })
    }

    private fun onItemClick(postData: PostData) {
        Timber.d("clicked on ${postData.title}")

        val realPostData = if (postData.crosspost_parent_list.isNullOrEmpty()) postData else postData.crosspost_parent_list.first()

        if (realPostData.url_overridden_by_dest != null && !realPostData.url_overridden_by_dest.isImageUrl()) {
            val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(realPostData.url_overridden_by_dest))
            ContextCompat.startActivity(requireContext(), intent, null)
        } else {
            Navigation.findNavController(requireView())
                .navigate(
                    PostListFragmentDirections.actionPostListFragmentToArticleFragment(realPostData, realPostData.title)
                )
        }
    }

    private fun updateList(posts: List<Thing<PostData>>) {
        Timber.d("Loaded")
        postItemAdapter.postList = posts
    }
}
