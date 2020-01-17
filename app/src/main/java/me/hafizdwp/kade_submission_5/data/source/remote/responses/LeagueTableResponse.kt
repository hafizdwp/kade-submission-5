package me.hafizdwp.kade_submission_5.data.source.remote.responses

/**
 * @author hafizdwp
 * 07/01/2020
 **/
data class LeagueTableResponse(
        var name: String?,
        var teamid: String?,
        var played: Int?,
        var goalsfor: Int?,
        var goalsagainst: Int?,
        var goalsdifference: Int?,
        var win: Int?,
        var draw: Int?,
        var loss: Int?,
        var total: Int?,

        // added custom
        var badgeUrl: String? = ""
)