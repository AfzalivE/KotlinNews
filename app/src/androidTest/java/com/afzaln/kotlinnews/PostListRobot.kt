package com.afzaln.kotlinnews

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

fun postListRobot(fn: PostListRobot.() -> Unit) = PostListRobot().apply(fn)

infix fun PostListRobot.verifyThat(fn: PostListChecks.() -> Unit) = PostListChecks().apply(fn)

class PostListRobot {

    fun tapSelfTextListItem() {
        onView(withId(R.id.post_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<PostViewHolder>(0, click()))
    }

    fun tapImageListItem() {

    }

    fun tapLinkListItem() {

    }

    fun tapErrorRefresh() {

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
