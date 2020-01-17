package me.hafizdwp.kade_submission_5.ui

import kotlinx.coroutines.delay
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseActivity
import me.hafizdwp.kade_submission_5.utils.extentions.launchMain

/**
 * @author hafizdwp
 * 15/12/2019
 **/
class SplashActivity : BaseActivity() {

    override val layoutRes: Int
        get() = R.layout.splash_activity

    override fun onExtractIntents() {
    }

    override fun onReady() {
        launchMain {
            delay(2000L)
            MainActivity.startActivity(this@SplashActivity)
            this@SplashActivity.finish()
        }
    }
}