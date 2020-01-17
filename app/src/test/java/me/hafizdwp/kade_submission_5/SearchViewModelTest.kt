package me.hafizdwp.kade_submission_5

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.search.SearchViewModel
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.fromJson
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * @author hafizdwp
 * 09/01/2020
 **/
class SearchViewModelTest {

    // A JUnit Test Rule that swaps the background executor used by
    // the Architecture Components with a different one which executes each task synchronously.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel
    @Mock
    private lateinit var repository: MyRepository
    @Mock
    private lateinit var application: Application


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(application, repository)
    }

    @Test
    fun `mock test for get search matches`() {
        val fakeListInString = DummyTest.searchList
        val fakeList = fakeListInString.fromJson<List<MatchResponse>>()
        val fakeResponse = MutableLiveData<ResultState<List<MatchResponse>>>()
        fakeResponse.value = ResultState.Success(fakeList)

        // when thenc
        Mockito.`when`(repository.getMatchesByKeyword("Barcelona")).thenReturn(fakeResponse)

        val observer = mock<Observer<ResultState<List<MatchResponse>>>>()
        viewModel.matchesLD.observeForever(observer)
        viewModel.searchMatch("Barcelona")

        ArgumentCaptor.forClass(ResultState::class.java).run {

            // verify
            verify(observer).onChanged(capture() as ResultState<List<MatchResponse>>?)

            // asserts
            Assert.assertNotNull(value)
            Assert.assertEquals(fakeResponse.value, (value as ResultState.Success<List<MatchResponse>>))
        }
    }
}