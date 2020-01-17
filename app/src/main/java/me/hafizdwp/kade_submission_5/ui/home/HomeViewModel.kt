package me.hafizdwp.kade_submission_5.ui.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hafizdwp.kade_submission_5.base.BaseViewModel
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.log
import me.hafizdwp.kade_submission_5.utils.extentions.logError
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent
import java.text.SimpleDateFormat

/**
 * @author hafizdwp
 * 17/12/2019
 **/
class HomeViewModel(application: Application,
                    private val repository: MyRepository) : BaseViewModel(application) {

    val leagueId = 4328 // BPL

    val upmatchesLD = SingleLiveEvent<List<MatchResponse>>()
    val recentLD = SingleLiveEvent<List<MatchResponse>>()
    private val upmatchesArray = arrayListOf<MatchResponse>()
    private val recentArray = arrayListOf<MatchResponse>()


    fun getRecentMatches() = viewModelScope.launch {
        val resultState = repository.getRecentMatches(leagueId)
        when (resultState) {
            is ResultState.Success -> {

                val size = if (resultState.data.size > 4)
                    4
                else resultState.data.size

                for (i in 0..size) {
                    if (i <= 5) {
                        log("getting team details for RECENT [$i]")
                        withContext(Dispatchers.Default) {
                            getTeamDetails(i, resultState.data[i], MatchType.RECENT)
                        }

                    } else
                        break
                }

                recentLD.value = recentArray
            }
        }
    }

    fun getUpcomingMatches() = viewModelScope.launch {
        val resultState = repository.getUpcomingMatches(leagueId)
        when (resultState) {
            is ResultState.Success -> {

                val size = if (resultState.data.size > 2)
                    2
                else resultState.data.size

                for (i in 0..size) {
                    if (i <= 3) {
                        log("getting team details for UPCOMING [$i]")
                        withContext(Dispatchers.Default) {
                            getTeamDetails(i, resultState.data[i], MatchType.UPCOMING)
                        }

                    } else
                        break
                }

                upmatchesLD.value = upmatchesArray
            }
            is ResultState.Error -> {
                logError(resultState.error)
            }
        }
    }

    private suspend fun getTeamDetails(index: Int,
                                       matchResponse: MatchResponse,
                                       type: MatchType) {

        val matchData= matchResponse.copy()

        // pre-set the full data
        val serverFormat = SimpleDateFormat("yyyy-MM-dd")
        val serverDate = serverFormat.parse(matchResponse.dateEvent)
        val expectedFormat = SimpleDateFormat("EEEE, dd MMM yyyy")
        val expectedDate = expectedFormat.format(serverDate)

        matchData.dateEvent = expectedDate

        val homeId = matchResponse.idHomeTeam
        val homeResult = repository.getTeamDetails((homeId ?: "0").toInt())
        log("get home [$index]")
        when (homeResult) {
            is ResultState.Success -> {
                homeResult.data[0].apply {
                    matchData.homeTeamBadge = strTeamBadge ?: ""
                    matchData.homeTeamName = strTeam ?: ""
                    matchData.stadium = strStadium ?: ""
                }
            }
            is ResultState.Error -> {
                logError(homeResult.error)
            }
        }

        val awayId = matchResponse.idAwayTeam
        val awayResult = repository.getTeamDetails((awayId ?: "0").toInt())
        log("get away [$index]")
        when (awayResult) {
            is ResultState.Success -> {
                awayResult.data[0].apply {
                    matchData.awayTeamBadge = strTeamBadge ?: ""
                    matchData.awayTeamName = strTeam ?: ""
                }
            }
            is ResultState.Error -> {
                logError(awayResult.error)
            }
        }

        when (type) {
            MatchType.UPCOMING -> upmatchesArray.add(matchData)
            MatchType.RECENT -> recentArray.add(matchData)
        }
    }

    private enum class MatchType {
        RECENT, UPCOMING
    }
}