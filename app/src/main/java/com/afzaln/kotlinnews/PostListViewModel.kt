package com.afzaln.kotlinnews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afzaln.kotlinnews.data.RedditRepository
import com.afzaln.kotlinnews.data.models.ListingData
import com.afzaln.kotlinnews.data.models.Thing
import kotlinx.coroutines.launch

class PostListViewModel(private val repository: RedditRepository): ViewModel() {

    private val postsLiveData: MutableLiveData<Thing<ListingData>> = MutableLiveData()

    fun fetchPosts(): MutableLiveData<Thing<ListingData>> {
        viewModelScope.launch {
            val posts = repository.fetchPosts("kotlin")
            postsLiveData.postValue(posts)
        }

        return postsLiveData
    }
}
