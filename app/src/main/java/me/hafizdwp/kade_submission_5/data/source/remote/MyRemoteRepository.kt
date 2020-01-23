package me.hafizdwp.kade_submission_5.data.source.remote

import me.hafizdwp.kade_submission_5.data.ApiServiceFactory
import me.hafizdwp.kade_submission_5.data.source.remote.api.FootballApi
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueDetailResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueTableResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.fetchEvent
import me.hafizdwp.kade_submission_5.utils.extentions.fetchEvents
import me.hafizdwp.kade_submission_5.utils.extentions.fetchLeagues
import me.hafizdwp.kade_submission_5.utils.extentions.fetchTable
import me.hafizdwp.kade_submission_5.utils.extentions.fetchTeam

/**
 * @author hafizdwp
 * 20/11/2019
 **/
class MyRemoteRepository {

    val footballApi by lazy { ApiServiceFactory.build<FootballApi>() }


    suspend fun getTeamsByKeyword(query: String): ResultState<List<TeamResponse>> {
        return fetchTeam {
            footballApi.getTeamsByKeyword(query)
        }
    }

    suspend fun getMatchesByKeyword(query: String): ResultState<List<MatchResponse>> {
        return fetchEvent {
            footballApi.getMatchesByKeyword(query)
        }
    }

    suspend fun getLeagueTable(leagueId: Int): ResultState<List<LeagueTableResponse>> {
        return fetchTable {
            footballApi.getLeagueTable(leagueId)
        }
    }

    suspend fun getRecentMatches(leagueId: Int): ResultState<List<MatchResponse>> {
        return fetchEvents {
            footballApi.getRecentMatches(leagueId)
        }
    }

    suspend fun getLeagueDetail(leagueId: Int): ResultState<List<LeagueDetailResponse>> {
        return fetchLeagues {
            footballApi.getLeagueDetail(leagueId)
        }
    }

    suspend fun getUpcomingMatches(leagueId: Int): ResultState<List<MatchResponse>> {
        return fetchEvents {
            footballApi.getUpcomingMatches(leagueId)
        }
    }

    suspend fun getTeamDetails(teamId: Int): ResultState<List<TeamResponse>> {
        return fetchTeam {
            footballApi.getTeamDetails(teamId)
        }
    }

    suspend fun getMatchDetail(eventId: Int): ResultState<List<MatchResponse>> {
        return fetchEvents {
            footballApi.getMatchDetail(eventId)
        }
    }

    companion object {
        private val INSTANCE: MyRemoteRepository? = null
        fun getInstance() = INSTANCE ?: MyRemoteRepository()
    }
}