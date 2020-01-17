package me.hafizdwp.kade_submission_5.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import me.hafizdwp.kade_submission_5.MyApp
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.extentions.fromJson
import me.hafizdwp.kade_submission_5.utils.extentions.log
import me.hafizdwp.kade_submission_5.utils.extentions.toJson
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * @author hafizdwp
 * 20/11/2019
 **/
class MyLocalRepository {

    val Context.database: MyDbHelper
        get() = MyDbHelper.getInstance(this)

    fun useDb(action: SQLiteDatabase.() -> Unit) {
        MyApp.getContext()?.database?.use(action)
    }


    fun saveOrDeleteFavorite(matchInJson: String): MatchResponse.FavoriteState? {
        var favoriteState: MatchResponse.FavoriteState? = null
        try {
            val matchResponse = matchInJson.fromJson<MatchResponse>()
            if (matchResponse.isFavorited == true) {
                // delete
                useDb {
                    delete(
                            tableName = MatchResponse.TABLE_NAME,
                            whereClause = "(${MatchResponse.COLUMN_ID} = {id})",
                            args = *arrayOf(MatchResponse.COLUMN_ID to matchResponse.idEvent!!)
                    )
                }
                favoriteState = MatchResponse.FavoriteState.DELETED

                log("match deleted")
            } else {
                // insert
                matchResponse.isFavorited = true
                useDb {
                    insert(
                            tableName = MatchResponse.TABLE_NAME,
                            values = *arrayOf(
                                    MatchResponse.COLUMN_ID to matchResponse.idEvent!!,
                                    MatchResponse.COLUMN_MATCH_IN_JSON to matchResponse.toJson()))
                }
                favoriteState = MatchResponse.FavoriteState.SAVED

                log("match saved")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return favoriteState
    }

    fun getFavoriteIfExist(eventId: Int): MatchResponse? {
        var responseInTable: MatchResponse.Table?
        var response: MatchResponse? = null
        try {
            useDb {
                val query = select(
                        tableName = MatchResponse.TABLE_NAME
                ).whereArgs("(${MatchResponse.COLUMN_ID} = {eventId})",
                        "eventId" to eventId)
                responseInTable = query.parseSingle(classParser())
                response = responseInTable?.match_in_json?.fromJson()

                log("match exists")
            }
        } catch (e: Exception) {
            e.printStackTrace()

            log("match dont exists")
        }

        return response
    }

    fun getAllFavorites(): List<MatchResponse> {
        val list = arrayListOf<MatchResponse>()
        try {
            useDb {
                val query = select(tableName = MatchResponse.TABLE_NAME)
                val response = query.parseList(classParser<MatchResponse.Table>())
                list.clear()
                response.forEach {
                    list.add(it.match_in_json.fromJson())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list
    }

    companion object {
        private val INSTANCE: MyLocalRepository? = null
        fun getInstance() = INSTANCE ?: MyLocalRepository()
    }
}