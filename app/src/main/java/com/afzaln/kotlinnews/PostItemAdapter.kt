package com.afzaln.kotlinnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.afzaln.kotlinnews.data.models.PostData
import com.afzaln.kotlinnews.data.models.Thing
import com.afzaln.kotlinnews.databinding.PostItemBinding
import timber.log.Timber

class PostItemAdapter : RecyclerView.Adapter<PostViewHolder>() {

    var postList: List<Thing<PostData>> = arrayListOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(PostDiffCallback(field, value))
            field = value
            diff.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) = holder.bind(postList[position])

    override fun getItemCount(): Int = postList.size

}

class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {

    lateinit var post: Thing<PostData>

    init {
        binding.root.setOnClickListener {
            Timber.d("clicked on ${post.data.title}")
        }
    }

    fun bind(post: Thing<PostData>) {
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
