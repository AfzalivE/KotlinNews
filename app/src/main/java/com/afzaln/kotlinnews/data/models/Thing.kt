package com.afzaln.kotlinnews.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class Thing<out T : Data>(
    val id: String?,
    val name: String?,
    val kind: String,
    val data: T
)

abstract class Data

@JsonClass(generateAdapter = true)
data class ListingData(
    val before: String?,
    val after: String,
    val modhash: String,
    val children: List<Thing<PostData>>
) : Data()

@JsonClass(generateAdapter = true)
data class PostData(
    val subreddit: String,
    val id: String,
    val title: String,
    val url: String
) : Data()
