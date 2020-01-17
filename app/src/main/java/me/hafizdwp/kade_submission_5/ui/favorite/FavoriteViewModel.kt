package me.hafizdwp.kade_submission_5.ui.favorite

import android.app.Application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_5.base.BaseViewModel
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent

/**
 * @author hafizdwp
 * 08/01/2020
 **/
class FavoriteViewModel(application: Application,
                        val repository: MyRepository) : BaseViewModel(application) {

    val matchLD = SingleLiveEvent<List<MatchResponse>>()


    fun getAllFavorite() = viewModelScope.launch {
        val matchData = repository.getAllFavorites()
        if (matchData.isEmpty())
            matchLD.value = null
        else
            matchLD.value = matchData
    }
}