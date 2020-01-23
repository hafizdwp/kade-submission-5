package me.hafizdwp.kade_submission_5.ui._listener

import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse

/**
 * @author hafizdwp
 * 23/01/2020
 **/
interface TeamClickListener {
    fun onTeamClick(data: TeamResponse)
}