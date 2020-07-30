package com.afzaln.kotlinnews

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afzaln.kotlinnews.data.RedditApiService
import com.afzaln.kotlinnews.data.RedditRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Injector private constructor() {

    private val redditApi: RedditApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(RedditApiService::class.java)
    }

    val redditRepository: RedditRepository by lazy {
        RedditRepository(redditApi)
    }

    companion object {
        @SuppressLint("StaticFieldLeak", "Keeping app context is safe here")
        @Volatile
        private lateinit var INSTANCE: Injector

        var BASE_URL = "https://www.reddit.com/api/v1/"

        fun init() {
            INSTANCE = Injector()
        }

        fun get(): Injector {
            return INSTANCE
        }
    }
}

class PostListViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.constructors[0].newInstance(Injector.get().redditRepository) as T
    }
}
