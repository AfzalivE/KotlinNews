package com.afzaln.kotlinnews

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.afzaln.kotlinnews.data.models.PostData
import com.afzaln.kotlinnews.data.models.Thing
import com.afzaln.kotlinnews.databinding.ImagePostItemBinding
import com.afzaln.kotlinnews.databinding.PostItemBinding
import com.bumptech.glide.Glide
import timber.log.Timber

class PostItemAdapter : RecyclerView.Adapter<PostViewHolder>() {

    var postList: List<Thing<PostData>> = arrayListOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(PostDiffCallback(field, value))
            field = value
            diff.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_IMAGE -> ImagePostViewHolder(ImagePostItemBinding.inflate(inflater, parent, false))
            TYPE_SELFTEXT -> SelfPostViewHolder(PostItemBinding.inflate(inflater, parent, false))
            else -> SelfPostViewHolder(PostItemBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(postList[position])

    override fun getItemCount(): Int = postList.size

    override fun getItemViewType(position: Int): Int {
        if (postList[position].data.url.isImageUrl()) {
            return TYPE_IMAGE
        }

        return TYPE_SELFTEXT
    }

    companion object {
        const val TYPE_SELFTEXT = 0
        const val TYPE_IMAGE = 1
    }
}

abstract class PostViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(post: Thing<PostData>)
}

class ImagePostViewHolder(private val binding: ImagePostItemBinding) : PostViewHolder(binding) {

    private lateinit var post: Thing<PostData>

    init {
        binding.root.setOnClickListener {
            Timber.d("clicked on ${post.data.title}")
            Navigation.findNavController(itemView)
                .navigate(
                    PostListFragmentDirections.actionPostListFragmentToArticleFragment(post.data, post.data.title)
                )
        }
    }

    override fun bind(post: Thing<PostData>) {
        this.post = post
        binding.title.text = post.data.title

        if (post.data.url.isImageUrl()) {
            Glide.with(itemView.context)
                .load(post.data.url)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.image)
        }
    }
}

class SelfPostViewHolder(private val binding: PostItemBinding) : PostViewHolder(binding) {

    private lateinit var post: Thing<PostData>

    init {
        binding.root.setOnClickListener {
            Timber.d("clicked on ${post.data.title}")
            if (post.data.url_overridden_by_dest != null && !post.data.url_overridden_by_dest!!.isImageUrl()) {
                val intent = Intent(Intent.ACTION_VIEW).setData(Uri.parse(post.data.url_overridden_by_dest))
                ContextCompat.startActivity(itemView.context, intent, null)
            } else {
                Navigation.findNavController(itemView)
                    .navigate(
                        PostListFragmentDirections.actionPostListFragmentToArticleFragment(post.data, post.data.title)
                    )
            }
        }
    }

    override fun bind(post: Thing<PostData>) {
        this.post = post
        binding.title.text = post.data.title
    }
}


class PostDiffCallback(
    private val oldList: List<Thing<PostData>>,
    private val newList: List<Thing<PostData>>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPost = oldList[oldItemPosition]
        val newPost = newList[newItemPosition]

        return oldPost == newPost
    }
}
