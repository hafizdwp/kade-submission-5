package me.hafizdwp.jetpack_submission_final.utils

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers

/**
 * @author hafizdwp
 * 02/12/2019
 **/

fun withId(@IdRes id: Int): ViewInteraction? {
    return Espresso.onView(ViewMatchers.withId(id))
}

fun ViewInteraction.isDisplayed() {
    this.apply {
        check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}

fun ViewInteraction.matchesWithText(text: String) {
    this.apply {
        check(ViewAssertions.matches(ViewMatchers.withText(text)))
    }
}

fun ViewInteraction.matchesWithText(resourceId: Int) {
    this.apply {
        check(ViewAssertions.matches(ViewMatchers.withText(resourceId)))
    }
}