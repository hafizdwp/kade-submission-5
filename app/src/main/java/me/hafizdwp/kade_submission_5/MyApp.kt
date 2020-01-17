package me.hafizdwp.kade_submission_5

import android.app.Application
import com.facebook.stetho.Stetho

/**
 * @author hafizdwp
 * 15/12/2019
 **/
class MyApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun getContext() = instance?.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        // Stetho
        Stetho.initializeWithDefaults(this)
    }
}