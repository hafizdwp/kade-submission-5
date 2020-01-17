package me.hafizdwp.kade_submission_5

import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import me.hafizdwp.jetpack_submission_final.utils.RecyclerViewItemCountAssertion
import me.hafizdwp.jetpack_submission_final.utils.isDisplayed
import me.hafizdwp.jetpack_submission_final.utils.withId
import me.hafizdwp.kade_submission_5.ui.search.SearchFragment
import me.hafizdwp.kade_submission_5.utils.test.EspressoIdlingResource
import me.hafizdwp.kade_submission_5.utils.test.SingleFragmentActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author hafizdwp
 * 09/01/2020
 **/
class SearchFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java)

    private val searchFragment = SearchFragment.newInstance()

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        activityRule.activity.setFragment(searchFragment)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun checkAllViews() {

        // Checks if views are displayed correctly
        withId(R.id.etSearch)?.apply {
            isDisplayed()
            ViewAssertions.matches(ViewMatchers.withHint(R.string.search))
        }
        withId(R.id.myProgressView)?.isDisplayed()

        // Checks to perform search
        // with query: "Barcelona"
        withId(R.id.etSearch)?.perform(ViewActions.clearText(), ViewActions.typeText("Barcelona"))
        runBlocking {
            delay(1500L)
            withId(R.id.recyclerSearch)?.apply {
                isDisplayed()
                check(RecyclerViewItemCountAssertion(unexpectedCount = 0))
            }
        }

        // Checks to perform search
        // with query: "Chelsea"
        withId(R.id.etSearch)?.perform(ViewActions.clearText(), ViewActions.typeText("Chelsea"))
        runBlocking {
            delay(1500L)
            withId(R.id.recyclerSearch)?.apply {
                isDisplayed()
                check(RecyclerViewItemCountAssertion(unexpectedCount = 0))
            }
        }

        // Checks to perform search
        // with query: "Everton"
        withId(R.id.etSearch)?.perform(ViewActions.clearText(), ViewActions.typeText("Everton"))
        runBlocking {
            delay(1500L)
            withId(R.id.recyclerSearch)?.apply {
                isDisplayed()
                check(RecyclerViewItemCountAssertion(unexpectedCount = 0))
            }
        }
    }
}