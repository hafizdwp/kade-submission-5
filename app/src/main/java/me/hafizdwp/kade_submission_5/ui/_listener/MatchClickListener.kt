package me.hafizdwp.kade_submission_5.ui._listener

import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse

/**
 * @author hafizdwp
 * 23/01/2020
 **/
interface MatchClickListener {
    fun onMatchClick(data: MatchResponse)
}