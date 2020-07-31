package com.afzaln.kotlinnews

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner

class MockTestRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, KotlinNewsTestApp::class.java.name, context)
    }
}

class KotlinNewsTestApp : KotlinNewsApp() {

    override fun onCreate() {
        Injector.BASE_URL = "http://127.0.0.1:8080"
        super.onCreate()
    }
}
