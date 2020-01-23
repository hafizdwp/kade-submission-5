package me.hafizdwp.kade_submission_5.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import me.hafizdwp.kade_submission_5.base.BaseViewModel
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent

/**
 * @author hafizdwp
 * 07/01/2020
 **/
class SearchViewModel(application: Application,
                      private val repository: MyRepository) : BaseViewModel(application) {

    private val MINIMUM_CHAR_FOR_SEARCH = 3

    private var query: String = ""
    private val searchTrigger = SingleLiveEvent<Void>()
    val matchesLD: LiveData<ResultState<List<MatchResponse>>> = Transformations.switchMap(searchTrigger) {
        repository.getMatchesByKeyword(query)
    }

    private var queryTeam: String = ""
    private val searchTeamTrigger = SingleLiveEvent<Void>()
    val teamsLD: LiveData<ResultState<List<TeamResponse>>> = Transformations.switchMap(searchTeamTrigger) {
        repository.getTeamsByKeyword(queryTeam)
    }

    fun searchTeams(query: String) {
        if (query.length >= MINIMUM_CHAR_FOR_SEARCH) {
            resetResultLD.call()

            this.queryTeam = query
            searchTeamTrigger.call()
        }
    }

    fun searchMatch(query: String) {
        if (query.length >= MINIMUM_CHAR_FOR_SEARCH) {
            resetResultLD.call()

            this.query = query
            searchTrigger.call()
        }
    }

    private var result = ""
    val resultTextLD = SingleLiveEvent<String>()
    val resetResultLD = SingleLiveEvent<Void>()
    private var resultMatchCount = -1
    private var resultTeamCount = -1
    private var isResultMatchUpdated = false
    private var isResultTeamUpdated = false

    fun updateMatchCount(size: Int) {
        resultMatchCount = size
        isResultMatchUpdated = true
        updateResultCounter()
    }

    fun updateTeamCount(size: Int) {
        resultTeamCount = size
        isResultTeamUpdated = true
        updateResultCounter()
    }

    fun updateResultCounter() {
        if (isResultMatchUpdated && isResultTeamUpdated) {
            if (resultMatchCount == 0 && resultTeamCount == 0) {
                result = "No result found"
            } else {
                result = when {
                    resultMatchCount <= 1 ->
                        "$resultMatchCount match"
                    resultMatchCount > 1 ->
                        "$resultMatchCount matches"
                    else -> ""
                }

                result += " and "

                result += when {
                    resultTeamCount <= 1 ->
                        "$resultTeamCount team found"
                    resultTeamCount > 1 ->
                        "$resultTeamCount teams found"
                    else -> ""
                }
            }

            resultTextLD.value = result
            isResultMatchUpdated = false
            isResultTeamUpdated = false
        }
    }
}