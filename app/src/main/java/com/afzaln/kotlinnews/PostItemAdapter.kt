package com.afzaln.kotlinnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.afzaln.kotlinnews.data.models.PostData
import com.afzaln.kotlinnews.data.models.Thing
import com.afzaln.kotlinnews.databinding.ImagePostItemBinding
import com.afzaln.kotlinnews.databinding.PostItemBinding
import com.bumptech.glide.Glide

class PostItemAdapter(private val clickListener: (PostData) -> Unit) : RecyclerView.Adapter<PostViewHolder>() {

    var postList: List<Thing<PostData>> = arrayListOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(PostDiffCallback(field, value))
            field = value
            diff.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_IMAGE -> {
                val binding = ImagePostItemBinding.inflate(inflater, parent, false)
                ImagePostViewHolder(binding, clickListener)
            }
            else -> {
                // self text
                val binding = PostItemBinding.inflate(inflater, parent, false)
                SelfPostViewHolder(binding, clickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(postList[position])

    override fun getItemCount(): Int = postList.size

    override fun getItemViewType(position: Int): Int {
        if (postList[position].data.thumbnailUrl.isImageUrl()
            || postList[position].data.url.isImageUrl()
        ) {
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

class ImagePostViewHolder(
    private val binding: ImagePostItemBinding,
    clickListener: (PostData) -> Unit
) : PostViewHolder(binding) {

    private lateinit var post: Thing<PostData>

    init {
        binding.root.setOnClickListener { clickListener(post.data) }
    }

    override fun bind(post: Thing<PostData>) {
        this.post = post
        binding.title.text = post.data.title

        val imageUrl = if (post.data.thumbnailUrl.isNotEmpty()) post.data.thumbnailUrl else post.data.url

        if (imageUrl.isImageUrl()) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .centerInside()
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.image)
        }
    }
}

class SelfPostViewHolder(
    private val binding: PostItemBinding,
    clickListener: (PostData) -> Unit
) : PostViewHolder(binding) {

    private lateinit var post: Thing<PostData>

    init {
        binding.root.setOnClickListener { clickListener(post.data) }
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
