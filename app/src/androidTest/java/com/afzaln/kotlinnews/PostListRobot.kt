package com.afzaln.kotlinnews

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.hamcrest.CoreMatchers.notNullValue

fun postList(fn: PostListRobot.() -> Unit) = PostListRobot().apply(fn)

infix fun PostListRobot.verifyThat(fn: PostListChecks.() -> Unit) = PostListChecks().apply(fn)

class PostListRobot {

    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    infix fun tapSelfTextListItem(fn: ArticleRobot.() -> Unit): ArticleRobot {
        onView(withId(R.id.post_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<PostViewHolder>(1, click())
            )

        return article(fn)
    }

    infix fun tapImageListItem(fn: ArticleRobot.() -> Unit): ArticleRobot {
        onView(withId(R.id.post_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<PostViewHolder>(0, click())
            )

        return article(fn)
    }

    infix fun tapLinkListItem(fn: ArticleRobot.() -> Unit): ArticleRobot {
        onView(withId(R.id.post_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<PostViewHolder>(5, click())
            )

        return article(fn)
    }

    fun tapErrorRefresh() {
        onView(withId(R.id.error_img)).perform(click())
    }

    fun waitForPostList() {
        val id = device.currentPackageName + ":id/post_list"
        assertThat(device.wait(Until.findObject((By.res(id))), 4000), notNullValue())
    }

    fun waitForError() {
        val id = device.currentPackageName + ":id/error_msg"
        assertThat(device.wait(Until.findObject((By.res(id))), 4000), notNullValue())
    }
}

class PostListChecks {
    fun loadingIsDisplayed() = onView(withId(R.id.loading)).check(matches(isDisplayed()))
    fun listIsDisplayed() = onView(withId(R.id.post_list)).check(matches(isDisplayed()))
    fun errorIsDisplayed() {
        onView(withId(R.id.error_img)).check(matches(isDisplayed()))
        onView(withId(R.id.error_msg)).check(matches(isDisplayed()))
    }
}
