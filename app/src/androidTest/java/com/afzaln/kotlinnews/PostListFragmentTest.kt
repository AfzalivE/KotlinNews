package com.afzaln.kotlinnews

import androidx.test.core.app.launchActivity
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class PostListFragmentTest {

    private var mockServer = MockServer()

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)


    @Before
    fun setUp() {
        mockServer.start()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun showLoadingWhenFetchingPosts() {
        launchActivity<MainActivity>()

        postList { } verifyThat {
            loadingIsDisplayed()
        }
    }

    @Test
    fun showErrorWhenNotLoaded() {
        mockServer.queueError()
        launchActivity<MainActivity>()

        postList {
            waitForError()
        } verifyThat {
            errorIsDisplayed()
        }
    }

    @Test
    fun clickingToRefreshAfterErrorReloads() {
        mockServer.queueError()
        launchActivity<MainActivity>()

        postList {
            waitForError()
            mockServer.queueSuccess()
            tapErrorRefresh()
        } verifyThat {
            listIsDisplayed()
        }
    }

    @Test
    fun showPostListWhenLoaded() {
        mockServer.queueSuccess()
        launchActivity<MainActivity>()

        postList {
            waitForPostList()
        } verifyThat {
            listIsDisplayed()
        }
    }

    @Test
    fun showArticleWhenSelfPostTapped() {
        mockServer.queueSuccess()
        launchActivity<MainActivity>()

        postList {
            waitForPostList()
        } tapSelfTextListItem {
        } verifyThat {
            articleBodyIsDisplayed()
        }
    }

    @Test
    fun showArticleImageWhenImagePostTapped() {
        mockServer.queueSuccess()
        launchActivity<MainActivity>()

        postList {
            waitForPostList()
        } tapImageListItem {
        } verifyThat {
            articleImageIsDisplayed()
        }
    }

    @Test
    fun openExternallyWhenLinkPostTapped() {
        mockServer.queueSuccess()
        launchActivity<MainActivity>()

        postList {
            waitForPostList()
        } tapLinkListItem {
        } verifyThat {
            articleOpenedExtenally()

        }
    }
}
