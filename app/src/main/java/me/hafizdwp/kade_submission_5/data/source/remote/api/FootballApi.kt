package me.hafizdwp.kade_submission_5.data.source.remote.api

import me.hafizdwp.kade_submission_5.base.BaseResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueDetailResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueTableResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author hafizdwp
 * 16/12/2019
 **/
interface FootballApi {

    @GET("lookupteam.php")
    suspend fun getTeamDetail(
            @Query("id") teamId: Int
    ): BaseResponse<List<TeamResponse>>

    @GET("searchteams.php")
    suspend fun getTeamsByKeyword(
            @Query("t") keyword: String
    ): BaseResponse<List<TeamResponse>>

    @GET("searchevents.php")
    suspend fun getMatchesByKeyword(
            @Query("e") keyword: String
    ): BaseResponse<List<MatchResponse>>

    @GET("lookuptable.php")
    suspend fun getLeagueTable(
            @Query("l") leagueId: Int,
            @Query("s") season: String = "1920"
    ): BaseResponse<List<LeagueTableResponse>>

    @GET("eventspastleague.php")
    suspend fun getRecentMatches(
            @Query("id") leagueId: Int
    ): BaseResponse<List<MatchResponse>>

    @GET("eventsnextleague.php")
    suspend fun getUpcomingMatches(
            @Query("id") leagueId: Int
    ): BaseResponse<List<MatchResponse>>

    @GET("lookupteam.php")
    suspend fun getTeamDetails(
            @Query("id") teamId: Int
    ): BaseResponse<List<TeamResponse>>

    @GET("lookupleague.php")
    suspend fun getLeagueDetail(
            @Query("id") leagueId: Int
    ): BaseResponse<List<LeagueDetailResponse>>

    @GET("lookupevent.php")
    suspend fun getMatchDetail(
            @Query("id") eventId: Int
    ): BaseResponse<List<MatchResponse>>
}