package com.afzaln.kotlinnews

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice




fun article(fn: ArticleRobot.() -> Unit) = ArticleRobot().apply(fn)

infix fun ArticleRobot.verifyThat(fn: ArticleChecks.() -> Unit) = ArticleChecks().apply(fn)

class ArticleRobot {

    private val device =  UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    fun tapBack(fn: PostListChecks.() -> Unit) {
        device.pressBack()
        PostListChecks().apply(fn)
    }

    fun tapUp(fn: PostListChecks.() -> Unit) {
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        PostListChecks().apply(fn)
    }
}

class ArticleChecks {
    fun articleBodyIsDisplayed() {
        onView(ViewMatchers.withId(R.id.body)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun articleImageIsDisplayed() {
        onView(ViewMatchers.withId(R.id.image)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun articleOpenedExtenally() {
        intended(IntentMatchers.hasAction(Intent.ACTION_VIEW))
    }
}
