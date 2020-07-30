package com.afzaln.kotlinnews.data.models

import android.os.Parcelable
import com.afzaln.kotlinnews.isImageUrl
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

abstract class Data : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ListingData(
    val before: String?,
    val after: String,
    val modhash: String,
    val children: List<Thing<PostData>>
) : Data()

@Parcelize
@JsonClass(generateAdapter = true)
data class PostData(
    val subreddit: String,
    val id: String,
    val title: String,
    val url: String,
    val selftext: String,
    val media: Media?,
    val thumbnail: String,
    val url_overridden_by_dest: String?,
    val crosspost_parent_list: List<PostData>?
) : Data() {

    val isLink: Boolean
        get() {
            return !url_overridden_by_dest.isNullOrEmpty() && !url_overridden_by_dest.isImageUrl()
        }

    val thumbnailUrl: String
        get() {
            return if (thumbnail.isNotEmpty() && "self" != thumbnail) {
                thumbnail
            } else {
                media?.thumbnailUrl ?: ""
            }
        }

    val isCrossPost: Boolean
        get() = !crosspost_parent_list.isNullOrEmpty()
}

@Parcelize
@JsonClass(generateAdapter = true)
data class Media(
    val type: String,
    val oembed: MediaData
) : Data() {
    val thumbnailUrl: String
        get() {
            return if (oembed.thumbnail_url.isNotEmpty()) {
                oembed.thumbnail_url
            } else {
                ""
            }
        }
}


@Parcelize
@JsonClass(generateAdapter = true)
class MediaData(
    val thumbnail_url: String
) : Data()


