package me.hafizdwp.kade_submission_5.ui.league

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hafizdwp.kade_submission_5.base.BaseViewModel
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueDetailResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueTableResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.log
import me.hafizdwp.kade_submission_5.utils.extentions.logError
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent

/**
 * @author hafizdwp
 * 23/12/2019
 **/
class LeagueDetailViewModel(application: Application,
                            private val repository: MyRepository) : BaseViewModel(application) {

    val clubrankProgressLD = SingleLiveEvent<Boolean>()
    val tablesLD = SingleLiveEvent<List<LeagueTableResponse>>()
    private val tablesArray = arrayListOf<LeagueTableResponse>()

    var mLeagueId = 0


    private val detailCallTrigger = SingleLiveEvent<Void>()
    val detailLeagueLD: LiveData<ResultState<LeagueDetailResponse>> = Transformations.switchMap(detailCallTrigger) {
        repository.getLeagueDetailLive(mLeagueId)
    }

    private val recentCallTrigger = SingleLiveEvent<Void>()
    val recentMatchesLD: LiveData<ResultState<List<MatchResponse>>> = Transformations.switchMap(recentCallTrigger) {
        repository.getRecentMatchesLive(mLeagueId)
    }

    private val upmatchesCallTrigger = SingleLiveEvent<Void>()
    val upmatchesLD: LiveData<ResultState<List<MatchResponse>>> = Transformations.switchMap(upmatchesCallTrigger) {
        repository.getUpmatchesLive(mLeagueId)
    }

    fun getLeagueDetail() = detailCallTrigger.call()
    fun getRecentMatches() = recentCallTrigger.call()
    fun getUpmatches() = upmatchesCallTrigger.call()

    suspend fun getLeagueTables(leagueId: Int = mLeagueId) {
        clubrankProgressLD.value = true
        val resultState = repository.getLeagueTable(leagueId)
        when (resultState) {
            is ResultState.Success -> {
                //tablesLD.value = resultState.data
                tablesArray.clear()
                tablesArray.addAll(resultState.data)

                tablesArray.forEachIndexed { index, leagueTableResponse ->
                    log("getting team BADGES $index$")

                    withContext(Dispatchers.Default) {
                        getTeamBadges(index, leagueTableResponse)
                    }
                }

                tablesLD.value = tablesArray
                clubrankProgressLD.value = false
            }

            is ResultState.Error -> {
                logError(resultState.error)
                clubrankProgressLD.value = false
            }
        }
    }

    private suspend fun getTeamBadges(index: Int, tableResponse: LeagueTableResponse) {
        val resultState = repository.getTeamDetails((tableResponse.teamid ?: "0").toInt())
        when (resultState) {
            is ResultState.Success -> {
                tablesArray[index].badgeUrl = resultState.data[0].strTeamBadge
            }

            is ResultState.Error -> {
                logError(resultState.error)
            }
        }
    }
}