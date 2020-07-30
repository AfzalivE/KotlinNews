package com.afzaln.kotlinnews.data

import com.afzaln.kotlinnews.data.models.ListingData
import com.afzaln.kotlinnews.data.models.Thing
import java.io.IOException

class RedditRepository(private val redditApiService: RedditApiService) {

    suspend fun fetchPosts(subreddit: String): Thing<ListingData> {
        val response = redditApiService.fetchPosts(subreddit)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw IOException(response.code().toString())
        }
    }

}
