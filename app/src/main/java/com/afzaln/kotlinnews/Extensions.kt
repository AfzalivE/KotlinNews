package com.afzaln.kotlinnews

fun String.isImageUrl(): Boolean {
    return IMAGE_FORMATS.contains(substringAfterLast("."))
}

val IMAGE_FORMATS = listOf("png", "jpg", "gif", "webp")
