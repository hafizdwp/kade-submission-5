package me.hafizdwp.kade_submission_5.data.source.remote.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author hafizdwp
 * 30/10/2019
 **/
@Parcelize
data class MatchResponse(
        var idEvent: String?,
        var idSoccerXML: String?,
        var strEvent: String?,
        var strEventAlternate: String?,
        var strFilename: String?,
        var strSport: String?,
        var idLeague: String?,
        var strLeague: String?,
        var strSeason: String?,
        var strDescriptionEN: String?,
        var strHomeTeam: String?,
        var strAwayTeam: String?,
        var intHomeScore: String?,
        var intRound: String?,
        var intAwayScore: String?,
        var strHomeGoalDetails: String?,
        var strHomeRedCards: String?,
        var strHomeYellowCards: String?,
        var strHomeLineupGoalkeeper: String?,
        var strHomeLineupDefense: String?,
        var strHomeLineupMidfield: String?,
        var strHomeLineupForward: String?,
        var strHomeLineupSubstitutes: String?,
        var strAwayRedCards: String?,
        var strAwayYellowCards: String?,
        var strAwayGoalDetails: String?,
        var strAwayLineupGoalkeeper: String?,
        var strAwayLineupDefense: String?,
        var strAwayLineupMidfield: String?,
        var strAwayLineupForward: String?,
        var strAwayLineupSubstitutes: String?,
        var dateEvent: String?,
        var dateEventLocal: String?,
        var strDate: String?,
        var strTime: String?,
        var strTimeLocal: String?,
        var idHomeTeam: String?,
        var idAwayTeam: String?,
        var strResult: String?,
        var strThumb: String?,
        var strTweet1: String?,
        var strTweet2: String?,
        var strTweet3: String?,
        var strVideo: String?,
        var strLocked: String?,

        // tambahan
        var stadium: String? = "",
        var homeTeamBadge: String? = "",
        var awayTeamBadge: String? = "",
        var homeTeamName: String? = "",
        var awayTeamName: String? = "",
        var isFavorited: Boolean? = false
) : Parcelable {

    companion object {
        const val TABLE_NAME = "favorite_table"
        const val COLUMN_ID = "id"
        const val COLUMN_MATCH_IN_JSON = "match_in_json"
    }

    enum class FavoriteState {
        SAVED, DELETED
    }

    data class Table(
            var id: Int,
            var match_in_json: String
    )
}