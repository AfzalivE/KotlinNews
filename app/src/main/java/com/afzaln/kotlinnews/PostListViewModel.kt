package com.afzaln.kotlinnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afzaln.kotlinnews.PostListUiState.*
import com.afzaln.kotlinnews.data.RedditRepository
import com.afzaln.kotlinnews.data.models.ListingData
import com.afzaln.kotlinnews.data.models.Thing
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.IOException

class PostListViewModel(private val repository: RedditRepository) : ViewModel() {

    private val _postsLiveData: MutableLiveData<PostListUiState> = MutableLiveData()
    val postsLiveData: LiveData<PostListUiState>
        get() = _postsLiveData

    fun fetchPosts(forceRefresh: Boolean = false) {
        // avoid reloading the data if already loaded
        if (_postsLiveData.value is Loaded && !forceRefresh) {
            return
        }
        viewModelScope.launch {
            _postsLiveData.postValue(Loading)
            try {
                val posts = repository.fetchPosts("kotlin")
                _postsLiveData.postValue(Loaded(posts))
            } catch (ex: IOException) {
                _postsLiveData.postValue(Error)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}


sealed class PostListUiState {
    object Loading : PostListUiState()
    class Loaded(val posts: Thing<ListingData>) : PostListUiState()
    object Error : PostListUiState()
}
