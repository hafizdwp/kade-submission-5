package me.hafizdwp.kade_submission_5.utils

/**
 * @author hafizdwp
 * 01/12/2019
 **/
sealed class MyRequestState {
    object Loading : MyRequestState()
    object Success : MyRequestState()
    class Empty(val emptyMsg: String?) : MyRequestState()
    class Failed(val errorMsg: String?) : MyRequestState()
}