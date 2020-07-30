package com.afzaln.kotlinnews

import android.app.Application
import timber.log.Timber

open class KotlinNewsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Injector.init()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
