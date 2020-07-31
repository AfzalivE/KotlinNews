package com.afzaln.kotlinnews

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArticleFragmentTest {

    private var mockServer = MockServer()

    @Before
    fun setUp() {
        mockServer.start()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun returnToPostListWhenBackTappedInArticle() {
        mockServer.queueSuccess()
        launchActivity<MainActivity>()

        postList {
            waitForPostList()
        } tapSelfTextListItem {
            tapBack {
                listIsDisplayed()
            }
        }
    }

    @Test
    fun returnToPostListWhenUpTappedInArticle() {
        mockServer.queueSuccess()
        launchActivity<MainActivity>()

        postList {
            waitForPostList()
        } tapSelfTextListItem {
            tapUp {
                listIsDisplayed()
            }
        }
    }
}
