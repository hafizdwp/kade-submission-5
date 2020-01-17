package me.hafizdwp.kade_submission_5.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import me.hafizdwp.kade_submission_5.base.BaseViewModel
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent

/**
 * @author hafizdwp
 * 07/01/2020
 **/
class SearchViewModel(application: Application,
                      val repository: MyRepository) : BaseViewModel(application) {

    private var query: String = ""
    private val searchTrigger = SingleLiveEvent<Void>()
    val matchesLD: LiveData<ResultState<List<MatchResponse>>> = Transformations.switchMap(searchTrigger) {
        repository.getMatchesByKeyword(query)
    }

    fun searchMatch(query: String) {
        this.query = query
        searchTrigger.call()
    }
}