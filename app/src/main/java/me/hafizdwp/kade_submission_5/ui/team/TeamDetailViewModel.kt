package me.hafizdwp.kade_submission_5.ui.team

import android.app.Application
import androidx.lifecycle.Transformations
import me.hafizdwp.kade_submission_5.base.BaseViewModel
import me.hafizdwp.kade_submission_5.data.source.MyRepository
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent

/**
 * @author hafizdwp
 * 23/01/2020
 **/
class TeamDetailViewModel(app: Application,
                          private val mRepository: MyRepository) : BaseViewModel(app) {

    var teamId = 0


    private val teamDetailTrigger = SingleLiveEvent<Void>()
    val teamDetailsLD = Transformations.switchMap(teamDetailTrigger) {
        mRepository.getTeamDetail(teamId)
    }

    fun getTeamDetail() = teamDetailTrigger.call()
}