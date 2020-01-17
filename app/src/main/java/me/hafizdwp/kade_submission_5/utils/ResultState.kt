package me.hafizdwp.kade_submission_5.utils

/**
 * @author hafizdwp
 * 16/12/2019
 **/
sealed class ResultState<out T : Any> {
    data class Success<T : Any>(var data: T) : ResultState<T>()
    data class Error(val error: String?) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
}