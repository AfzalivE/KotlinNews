package com.afzaln.kotlinnews.data.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
open class Thing<out T : Data>(
    val id: String?,
    val name: String?,
    val kind: String,
    val data: T
) : Parcelable

abstract class Data: Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ListingData(
    val before: String?,
    val after: String,
    val modhash: String,
    val children: List<Thing<PostData>>
) : Data(), Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class PostData(
    val subreddit: String,
    val id: String,
    val title: String,
    val url: String,
    val selftext: String,
    val url_overridden_by_dest: String?
) : Data(), Parcelable
