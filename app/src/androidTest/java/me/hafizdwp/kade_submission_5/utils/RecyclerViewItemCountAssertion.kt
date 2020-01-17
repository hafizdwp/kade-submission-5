package me.hafizdwp.jetpack_submission_final.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull


/**
 * @author hafizdwp
 * 16/11/2019
 **/
class RecyclerViewItemCountAssertion(val expectedCount: Int? = null,
                                     val unexpectedCount: Int? = null) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter


        assertNotNull(adapter)

        expectedCount?.let {
            assertThat(expectedCount, `is`(recyclerView.adapter!!.itemCount))
        }
        unexpectedCount?.let {
            assertNotEquals(unexpectedCount, recyclerView.adapter!!.itemCount)
        }
    }
}