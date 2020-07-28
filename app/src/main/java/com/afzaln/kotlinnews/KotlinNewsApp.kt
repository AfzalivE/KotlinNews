package com.afzaln.kotlinnews

import android.app.Application
import timber.log.Timber

class KotlinNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.init(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
