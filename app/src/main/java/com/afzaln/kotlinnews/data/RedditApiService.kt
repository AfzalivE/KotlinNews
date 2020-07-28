package com.afzaln.kotlinnews.data

import com.afzaln.kotlinnews.data.models.ListingData
import com.afzaln.kotlinnews.data.models.Thing
import retrofit2.http.GET
import retrofit2.http.Path

interface RedditApiService {

    @GET("/r/{subreddit}/.json")
    suspend fun fetchPosts(@Path("subreddit") subreddit: String): Thing<ListingData>
}
