package me.hafizdwp.kade_submission_5.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.hafizdwp.kade_submission_5.data.source.local.MyLocalRepository
import me.hafizdwp.kade_submission_5.data.source.remote.MyRemoteRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueDetailResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueTableResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.largeLog
import me.hafizdwp.kade_submission_5.utils.extentions.log
import me.hafizdwp.kade_submission_5.utils.extentions.logError
import me.hafizdwp.kade_submission_5.utils.extentions.toJson
import me.hafizdwp.kade_submission_5.utils.test.EspressoIdlingResource
import java.text.SimpleDateFormat

/**
 * @author hafizdwp
 * 20/11/2019
 **/
open class MyRepository {

    val remoteRepository = MyRemoteRepository.getInstance()
    val localRepository = MyLocalRepository.getInstance()


    // anjis jagoan
    private inline fun <reified T : Any, reified RS : ResultState<T>> asLiveData(
            crossinline repoFunction: suspend () -> RS): LiveData<ResultState<T>> {

        EspressoIdlingResource.increment()
        return liveData {
            emit(ResultState.Loading)
            val result = repoFunction()
            val resultLive = MutableLiveData<ResultState<T>>()
            when (result) {
                is ResultState.Success<*> -> {
                    resultLive.value = result
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }

                is ResultState.Error -> {
                    emit(result)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    fun getTeamDetail(teamId: Int): LiveData<ResultState<List<TeamResponse>>> {
        return asLiveData {
            remoteRepository.getTeamDetail(teamId)
        }
    }

    fun saveOrDeleteFavorite(matchInJson: String): MatchResponse.FavoriteState? {
        return localRepository.saveOrDeleteFavorite(matchInJson)
    }

    fun getFavoriteIfExist(eventId: Int): MatchResponse? {
        return localRepository.getFavoriteIfExist(eventId)
    }

    fun getAllFavorites(): List<MatchResponse> {
        return localRepository.getAllFavorites()
    }

    fun getTeamsByKeyword(query: String): LiveData<ResultState<List<TeamResponse>>> {
        EspressoIdlingResource.increment()
        return liveData {
            emit(ResultState.Loading)
            val result = remoteRepository.getTeamsByKeyword(query)
            val resultLive = MutableLiveData<ResultState<List<TeamResponse>>>()
            when (result) {
                is ResultState.Success -> {
                    resultLive.value = result
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }

                is ResultState.Error -> {
                    emit(result)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    fun getMatchesByKeyword(query: String): LiveData<ResultState<List<MatchResponse>>> {
        EspressoIdlingResource.increment()
        return liveData {
            emit(ResultState.Loading)
            val result = remoteRepository.getMatchesByKeyword(query)
            val resultLive = MutableLiveData<ResultState<List<MatchResponse>>>()
            when (result) {
                is ResultState.Success -> {

                    // get team details
                    val filteredList = filterSoccerOnlyAndFiveMatches(result.data)
                    val completeList = arrayListOf<MatchResponse>()
                    filteredList.forEachIndexed { index, matchResponse ->
                        withContext(Dispatchers.Default) {
                            completeList.add(getTeamDetails(index, matchResponse))
                        }
                    }

                    log("completeList STRING: $completeList")
                    log("completeList TOJSON: ${completeList.toJson()}")

                    // modify with completed list, & emit
                    result.data = completeList
                    resultLive.value = result
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }

                is ResultState.Error -> {
                    emit(result)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    fun getRecentMatchesLive(leagueId: Int): LiveData<ResultState<List<MatchResponse>>> {
        EspressoIdlingResource.increment()
        return liveData {
            emit(ResultState.Loading)
            val result = remoteRepository.getRecentMatches(leagueId)
            val resultLive = MutableLiveData<ResultState<List<MatchResponse>>>()
            when (result) {
                is ResultState.Success -> {

                    // get team details
                    val completeList = arrayListOf<MatchResponse>()
                    val desiredSize = if (result.data.size > 4)
                        4
                    else result.data.size
                    for (i in 0..desiredSize) {
                        if (i <= 5) {
                            withContext(Dispatchers.Default) {
                                completeList.add(getTeamDetails(i, result.data[i]))
                            }
                        } else {
                            break
                        }
                    }

                    largeLog("recentList JSON", completeList.toJson())

                    // modify with completed list, & emit
                    result.data = completeList
                    resultLive.value = result
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }

                is ResultState.Error -> {
                    emit(result)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    fun getUpmatchesLive(leagueId: Int): LiveData<ResultState<List<MatchResponse>>> {
        EspressoIdlingResource.increment()
        return liveData {
            emit(ResultState.Loading)
            val result = remoteRepository.getUpcomingMatches(leagueId)
            val resultLive = MutableLiveData<ResultState<List<MatchResponse>>>()
            when (result) {
                is ResultState.Success -> {

                    // get team details
                    val completeList = arrayListOf<MatchResponse>()
                    val desiredSize = if (result.data.size > 2)
                        2
                    else result.data.size
                    for (i in 0..desiredSize) {
                        if (i <= 3) {
                            withContext(Dispatchers.Default) {
                                completeList.add(getTeamDetails(i, result.data[i]))
                            }
                        } else {
                            break
                        }
                    }

                    largeLog("upmatchesList JSON", completeList.toJson())

                    // modify with completed list, & emit
                    result.data = completeList
                    resultLive.value = result
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }

                is ResultState.Error -> {
                    emit(result)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    fun getLeagueDetailLive(leagueId: Int): LiveData<ResultState<LeagueDetailResponse>> {
        EspressoIdlingResource.increment()
        return liveData {
            emit(ResultState.Loading)
            val result = remoteRepository.getLeagueDetail(leagueId)
            val resultLive = MutableLiveData<ResultState<LeagueDetailResponse>>()
            when (result) {
                is ResultState.Success -> {
                    resultLive.value = ResultState.Success(result.data[0])
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }

                is ResultState.Error -> {
                    emit(result)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }

    suspend fun getLeagueTable(leagueId: Int): ResultState<List<LeagueTableResponse>> {
        return remoteRepository.getLeagueTable(leagueId)
    }

    suspend fun getRecentMatches(leagueId: Int): ResultState<List<MatchResponse>> {
        return remoteRepository.getRecentMatches(leagueId)
    }

    suspend fun getUpcomingMatches(leagueId: Int): ResultState<List<MatchResponse>> {
        return remoteRepository.getUpcomingMatches(leagueId)
    }

    suspend fun getTeamDetails(teamId: Int): ResultState<List<TeamResponse>> {
        return remoteRepository.getTeamDetails(teamId)
    }

    suspend fun getMatchDetail(eventId: Int): ResultState<List<MatchResponse>> {
        return remoteRepository.getMatchDetail(eventId)
    }

    fun getMatchDetailsLive(eventId: Int): LiveData<ResultState<MatchResponse>> {
        // try local first
        val matchData = getFavoriteIfExist(eventId)
        if (matchData != null) {
            val resultLive = MutableLiveData<ResultState<MatchResponse>>()
            resultLive.value = ResultState.Success(matchData)
            return resultLive
        }

        // remote
        EspressoIdlingResource.increment()
        return liveData {
            emit(ResultState.Loading)
            val result = remoteRepository.getMatchDetail(eventId)
            val resultLive = MutableLiveData<ResultState<MatchResponse>>()
            when (result) {
                is ResultState.Success -> {

                    var updatedMatchData: MatchResponse? = null
                    withContext(Dispatchers.Default) {
                        updatedMatchData = getTeamDetails(0, result.data[0])
                    }
                    resultLive.value = ResultState.Success(updatedMatchData!!)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }

                is ResultState.Error -> {
                    emit(result)
                    emitSource(resultLive)
                    EspressoIdlingResource.decrement()
                }
            }
        }
    }


    companion object {

        private var INSTANCE: MyRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance() =
                INSTANCE ?: synchronized(MyRepository::class.java) {
                    INSTANCE ?: MyRepository()
                            .also { INSTANCE = it }
                }
    }

    /**
     * Add-ons
     * ---------------------------------------------------------------------------------------------
     * */
    private fun filterSoccerOnlyAndFiveMatches(list: List<MatchResponse>): List<MatchResponse> {
        val newList = list.filter {
            it.strSport == "Soccer"
        }.toMutableList()
        for (i in (newList.size - 1) downTo 5) {
            newList.removeAt(i)
        }

        log("myListSize: ${newList.size}")
        return newList
    }

    private suspend fun getTeamDetails(index: Int,
                                       matchResponse: MatchResponse): MatchResponse {

        val matchData = matchResponse.copy()

        // pre-set the full data
        val serverFormat = SimpleDateFormat("yyyy-MM-dd")
        val serverDate = serverFormat.parse(matchResponse.dateEvent)
        val expectedFormat = SimpleDateFormat("EEEE, dd MMM yyyy")
        val expectedDate = expectedFormat.format(serverDate)

        matchData.dateEvent = expectedDate

        val homeId = matchResponse.idHomeTeam
        val homeResult = getTeamDetails((homeId ?: "0").toInt())
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
        val awayResult = getTeamDetails((awayId ?: "0").toInt())
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

        return matchData
    }
}