package me.hafizdwp.kade_submission_5.ui.matches

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseViewModel
import me.hafizdwp.kade_submission_5.data.model.GnaData
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.toJson
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent

/**
 * @author hafizdwp
 * 06/01/2020
 **/
class MatchDetailViewModel(application: Application,
                           private val repository: MyRepository) : BaseViewModel(application) {

    var mMatchId: Int = 0 // eventId

    val goalsLD = SingleLiveEvent<List<GnaData>>()
    val shouldDisableGoalsLD = SingleLiveEvent<Boolean>() // true if match is not happened yet
    val shouldInflateFavoriteButtonLD = SingleLiveEvent<Boolean>()

    private val detailCallTrigger = SingleLiveEvent<Void>()
    val matchesLD: LiveData<ResultState<MatchResponse>> = Transformations.switchMap(detailCallTrigger) {
        repository.getMatchDetailsLive(mMatchId)
    }

    fun getDetailMatches() = detailCallTrigger.call()

    private fun getMatchData(): MatchResponse {
        return (matchesLD.value as ResultState.Success<MatchResponse>).data
    }

    fun saveOrDeleteFavorite() {
        var favoriteFinalStatus = false
        val matchData = getMatchData()
        val status = repository.saveOrDeleteFavorite(matchData.toJson())

        when (status) {
            MatchResponse.FavoriteState.SAVED -> {
                globalToast.value = R.string.match_favorited
                favoriteFinalStatus = true
            }
            MatchResponse.FavoriteState.DELETED -> {
                globalToast.value = R.string.match_unfavorited
                favoriteFinalStatus = false
            }
        }

        shouldInflateFavoriteButtonLD.value = favoriteFinalStatus
    }
}