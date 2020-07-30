package com.afzaln.kotlinnews

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PostListFragmentTest {

    private var mockWebServer = MockWebServer()

    @Before
    fun setUp() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun showLoadingWhenFetchingPosts() {
        launchActivity<MainActivity>()
        postListRobot { } verifyThat {
            loadingIsDisplayed()
        }
    }
}
