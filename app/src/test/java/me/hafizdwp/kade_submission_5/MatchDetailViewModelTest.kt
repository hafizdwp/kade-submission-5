package me.hafizdwp.kade_submission_5

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.matches.MatchDetailViewModel
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.fromJson
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * @author hafizdwp
 * 10/01/2020
 **/
class MatchDetailViewModelTest {

    // A JUnit Test Rule that swaps the background executor used by
    // the Architecture Components with a different one which executes each task synchronously.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MatchDetailViewModel
    @Mock
    private lateinit var repository: MyRepository
    @Mock
    private lateinit var application: Application


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MatchDetailViewModel(application, repository)
    }

    @Test
    fun `mock test for get match detail`() {
        val fakeMatchResponse = DummyTest.matchDetail.fromJson<MatchResponse>()
        val fakeResponse = MutableLiveData<ResultState<MatchResponse>>()
        fakeResponse.value = ResultState.Success(fakeMatchResponse)

        // when then
        Mockito.`when`(repository.getMatchDetailsLive(0)).thenReturn(fakeResponse)

        val observer = mock<Observer<ResultState<MatchResponse>>>()
        viewModel.matchesLD.observeForever(observer)
        viewModel.getDetailMatches()

        ArgumentCaptor.forClass(ResultState::class.java).run {

            // verify
            Mockito.verify(observer).onChanged(capture() as ResultState<MatchResponse>?)

            // asserts
            Assert.assertNotNull(value)
            Assert.assertEquals(fakeResponse.value, (value as ResultState.Success<MatchResponse>))
        }
    }
}