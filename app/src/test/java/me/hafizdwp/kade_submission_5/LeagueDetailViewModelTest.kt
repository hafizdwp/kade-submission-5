package me.hafizdwp.kade_submission_5

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueDetailResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.league.LeagueDetailViewModel
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
class LeagueDetailViewModelTest {

    // A JUnit Test Rule that swaps the background executor used by
    // the Architecture Components with a different one which executes each task synchronously.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: LeagueDetailViewModel
    @Mock
    private lateinit var repository: MyRepository
    @Mock
    private lateinit var application: Application


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = LeagueDetailViewModel(application, repository)
    }

    @Test
    fun `mock test for get league detail`() {
        val fakeLeagueDetailResponse = DummyTest.leagueDeteail.fromJson<LeagueDetailResponse>()
        val fakeResponse = MutableLiveData<ResultState<LeagueDetailResponse>>()
        fakeResponse.value = ResultState.Success(fakeLeagueDetailResponse)

        // when then
        Mockito.`when`(repository.getLeagueDetailLive(0)).thenReturn(fakeResponse)

        val observer = mock<Observer<ResultState<LeagueDetailResponse>>>()
        viewModel.detailLeagueLD.observeForever(observer)
        viewModel.getLeagueDetail()

        ArgumentCaptor.forClass(ResultState::class.java).run {

            // verify
            Mockito.verify(observer).onChanged(capture() as ResultState<LeagueDetailResponse>?)

            // asserts
            Assert.assertNotNull(value)
            Assert.assertEquals(fakeResponse.value, (value as ResultState.Success<LeagueDetailResponse>))
        }
    }

    @Test
    fun `mock test for get upcoming matches`() {
        val fakeListInString = DummyTest.upmatchesList
        val fakeList = fakeListInString.fromJson<List<MatchResponse>>()
        val fakeResponse = MutableLiveData<ResultState<List<MatchResponse>>>()
        fakeResponse.value = ResultState.Success(fakeList)

        // when then
        Mockito.`when`(repository.getUpmatchesLive(0)).thenReturn(fakeResponse)

        val observer = mock<Observer<ResultState<List<MatchResponse>>>>()
        viewModel.upmatchesLD.observeForever(observer)
        viewModel.getUpmatches()

        ArgumentCaptor.forClass(ResultState::class.java).run {

            // verify
            Mockito.verify(observer).onChanged(capture() as ResultState<List<MatchResponse>>?)

            // asserts
            Assert.assertNotNull(value)
            Assert.assertEquals(fakeResponse.value, (value as ResultState.Success<List<MatchResponse>>))
        }
    }

    @Test
    fun `mock test for get recent matches`() {
        val fakeListInString = DummyTest.recentList
        val fakeList = fakeListInString.fromJson<List<MatchResponse>>()
        val fakeResponse = MutableLiveData<ResultState<List<MatchResponse>>>()
        fakeResponse.value = ResultState.Success(fakeList)

        // when then
        Mockito.`when`(repository.getRecentMatchesLive(0)).thenReturn(fakeResponse)

        val observer = mock<Observer<ResultState<List<MatchResponse>>>>()
        viewModel.recentMatchesLD.observeForever(observer)
        viewModel.getRecentMatches()

        ArgumentCaptor.forClass(ResultState::class.java).run {

            // verify
            Mockito.verify(observer).onChanged(capture() as ResultState<List<MatchResponse>>?)

            // asserts
            Assert.assertNotNull(value)
            Assert.assertEquals(fakeResponse.value, (value as ResultState.Success<List<MatchResponse>>))
        }
    }
}