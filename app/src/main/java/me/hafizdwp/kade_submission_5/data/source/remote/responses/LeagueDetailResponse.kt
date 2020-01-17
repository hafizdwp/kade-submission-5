package me.hafizdwp.kade_submission_5.data.source.remote.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author hafizdwp
 * 30/10/2019
 **/

@Parcelize
data class LeagueDetailResponse(
        var idLeague: String?,
        var idSoccerXML: String?,
        var strSport: String?,
        var strLeague: String?,
        var strLeagueAlternate: String?,
        var strDivision: String?,
        var idCup: String?,
        var intFormedYear: String?,
        var dateFirstEvent: String?,
        var strGender: String?,
        var strCountry: String?,
        var strWebsite: String?,
        var strFacebook: String?,
        var strTwitter: String?,
        var strYoutube: String?,
        var strRSS: String?,
        var strDescriptionEN: String?,
        var strDescriptionDE: String?,
        var strDescriptionFR: String?,
        var strDescriptionIT: String?,
        var strDescriptionCN: String?,
        var strDescriptionJP: String?,
        var strDescriptionRU: String?,
        var strDescriptionES: String?,
        var strDescriptionPT: String?,
        var strDescriptionSE: String?,
        var strDescriptionNL: String?,
        var strDescriptionHU: String?,
        var strDescriptionNO: String?,
        var strDescriptionPL: String?,
        var strDescriptionIL: String?,
        var strFanart1: String?,
        var strFanart2: String?,
        var strFanart3: String?,
        var strFanart4: String?,
        var strBanner: String?,
        var strBadge: String?,
        var strLogo: String?,
        var strPoster: String?,
        var strTrophy: String?,
        var strNaming: String?,
        var strComplete: String?,
        var strLocked: String?
) : Parcelable