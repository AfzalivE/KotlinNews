package com.afzaln.kotlinnews

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afzaln.kotlinnews.data.RedditApiService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class Injector private constructor() {

    val redditApi: RedditApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.reddit.com/api/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        retrofit.create(RedditApiService::class.java)
    }

    companion object {
        @SuppressLint("StaticFieldLeak", "Keeping app context is safe here")
        @Volatile
        private lateinit var INSTANCE: Injector

        fun init(appContext: Context) {
            INSTANCE = Injector()
        }

        fun get(): Injector {
            return INSTANCE
        }
    }
}

class PostListViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.constructors[0].newInstance(Injector.get().redditApi) as T
    }
}
