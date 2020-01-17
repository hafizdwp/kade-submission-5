package me.hafizdwp.jetpack_submission_final.utils

import android.view.View
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.viewpager.widget.ViewPager
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull


/**
 * @author hafizdwp
 * 16/11/2019
 **/
class ViewPagerItemCountAssertion(val expectedCount: Int? = null,
                                  val unexpectedCount: Int? = null) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val viewPager = view as ViewPager
        val adapter = viewPager.adapter

        assertNotNull(viewPager)

        expectedCount?.let {
            assertThat(adapter?.count, `is`(expectedCount))
        }
        unexpectedCount?.let {
            assertNotEquals(adapter?.count, unexpectedCount)
        }
    }
}