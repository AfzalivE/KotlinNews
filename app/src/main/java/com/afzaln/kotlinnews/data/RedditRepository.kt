package com.afzaln.kotlinnews.data

import com.afzaln.kotlinnews.data.models.ListingData
import com.afzaln.kotlinnews.data.models.Thing

class RedditRepository(private val redditApiService: RedditApiService) {

    suspend fun fetchPosts(subreddit: String): Thing<ListingData> {
        return redditApiService.fetchPosts(subreddit)
    }

}
