package me.hafizdwp.kade_submission_5.ui.home

import me.hafizdwp.kade_submission_5.data.model.LeagueData
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse

/**
 * @author hafizdwp
 * 17/12/2019
 **/
interface HomeActionListener {
    fun onMatchClick(data: MatchResponse)
    fun onLeagueClick(data: LeagueData)
}