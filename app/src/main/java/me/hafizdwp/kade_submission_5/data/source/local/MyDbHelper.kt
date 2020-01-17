package me.hafizdwp.kade_submission_5.data.source.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable
import org.jetbrains.anko.db.dropTable

/**
 * @author hafizdwp
 * 08/11/2019
 **/
class MyDbHelper(context: Context) : ManagedSQLiteOpenHelper(context, DB_NAME, null, 1) {

    companion object {
        val DB_NAME = "leaguekeeper.db"

        var instance: MyDbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDbHelper {
            if (instance == null) {
                instance = MyDbHelper(ctx.applicationContext)
            }
            return instance as MyDbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
                tableName = MatchResponse.TABLE_NAME,
                ifNotExists = true,
                columns = *arrayOf(
                        MatchResponse.COLUMN_ID to INTEGER + PRIMARY_KEY,
                        MatchResponse.COLUMN_MATCH_IN_JSON to TEXT
                )
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.dropTable(MatchResponse.TABLE_NAME)
    }
}