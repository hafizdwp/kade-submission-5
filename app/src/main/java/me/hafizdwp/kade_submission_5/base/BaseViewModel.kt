package me.hafizdwp.kade_submission_5.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_5.data.model.GnaData
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.MyRequestState
import me.hafizdwp.kade_submission_5.utils.livedata.SingleLiveEvent
import kotlin.coroutines.CoroutineContext

/**
 * @author hafizdwp
 * 13/11/2019
 **/
open class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val globalToast = SingleLiveEvent<Int>()


    private val viewModelJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun launch(action: suspend CoroutineScope.() -> Unit):
            Job = CoroutineScope(coroutineContext).launch { action(this) }

    suspend fun <T> asyncAwait(context: CoroutineContext = Dispatchers.IO,
                               action: suspend CoroutineScope.() -> T): T =
            CoroutineScope(context).async { action(this) }.await()

//    fun MutableLiveData<MyRequestState>.loading() {
//        postValue(MyRequestState.Loading)
//    }
//
//    fun MutableLiveData<MyRequestState>.success() {
//        postValue(MyRequestState.Success)
//    }
//
//    fun MutableLiveData<MyRequestState>.empty(emptyMsg: String?) {
//        postValue(MyRequestState.Empty(emptyMsg))
//    }
//
//    fun MutableLiveData<MyRequestState>.failed(errorMsg: String?) {
//        postValue(MyRequestState.Failed(errorMsg))
//    }

    fun MutableLiveData<MyRequestState>.loading() {
        value = MyRequestState.Loading
    }

    fun MutableLiveData<MyRequestState>.success() {
        value = MyRequestState.Success
    }

    fun MutableLiveData<MyRequestState>.empty(emptyMsg: String?) {
        value = MyRequestState.Empty(emptyMsg)
    }

    fun MutableLiveData<MyRequestState>.failed(errorMsg: String?) {
        value = MyRequestState.Failed(errorMsg)
    }

    /**
     * Coroutine catch error message
     * ---------------------------------------------------------------------------------------------
     * */
//    fun Throwable.getCoroutineErrorMessage(): String {
//        return when (this) {
//            is HttpException -> {
//                when (this.code()) {
//                    -100, -200 ->
//                        app.getString(R.string.error_message_no_internet_connection)
//                    500, 502, 504 ->
//                        app.getString(R.string.error_message_server)
//                    429, 404 ->
//                        "Terjadi kesalahan dengan kode ${this.code()}"
//                    else ->
//                        app.getString(R.string.error_message_unexpected)
//                }
//            }
//            is UnknownHostException ->
//                app.getString(R.string.error_message_no_internet_connection)
//            is SocketTimeoutException ->
//                app.getString(R.string.error_message_no_internet_connection)
//            else ->
//                app.getString(R.string.error_message_unexpected)
//        }
//    }

    fun filterGoals(matchResponse: MatchResponse): List<GnaData> = with(matchResponse) {
        val sortedGoals = arrayListOf<GnaData>()

        val homeGoals = strHomeGoalDetails?.split(";")?.toMutableList()
        val awayGoals = strAwayGoalDetails?.split(";")?.toMutableList()
        homeGoals?.remove("")
        awayGoals?.remove("")

        homeGoals?.forEach { goal ->
            val minute = goal.substringBefore("'")
            val goalScorer = goal.substringAfter(":")

            sortedGoals.add(GnaData(minute, goalScorer, goalType = GnaData.GoalType.HOME))
        }
        awayGoals?.forEach { goal ->
            val minute = goal.substringBefore("'")
            val goalScorer = goal.substringAfter(":")

            sortedGoals.add(GnaData(minute, goalScorer, goalType = GnaData.GoalType.AWAY))
        }

        // sort
        sortedGoals.sortBy { it.minute }

        return sortedGoals
    }
}