package com.afzaln.kotlinnews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.afzaln.kotlinnews.data.models.PostData
import com.afzaln.kotlinnews.databinding.FragmentArticleBinding
import com.bumptech.glide.Glide

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentArticleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postData = args.postData

        showImage(postData)
        showSelfText(postData)
    }

    private fun showSelfText(postData: PostData) {
        binding.body.text = postData.selftext
    }

    private fun showImage(postData: PostData) {
        if (postData.url.isImageUrl()) {
            Glide.with(this).load(postData.url).into(binding.image)
        } else if (postData.thumbnailUrl.isNotEmpty()) {
            Glide.with(this).load(postData.thumbnailUrl).into(binding.image)
        }
    }
}
