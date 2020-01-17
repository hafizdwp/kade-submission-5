package me.hafizdwp.kade_submission_5.ui

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseActivity
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.favorite.FavoriteFragment
import me.hafizdwp.kade_submission_5.ui.home.HomeFragment
import me.hafizdwp.kade_submission_5.ui.search.SearchFragment
import me.hafizdwp.kade_submission_5.utils.extentions.fromJson
import me.hafizdwp.kade_submission_5.utils.extentions.log
import me.hafizdwp.kade_submission_5.utils.extentions.toastSpammable

class MainActivity : BaseActivity() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override val layoutRes: Int
        get() = R.layout.main_activity

    var isDoubleBackPressed = false
    val mFragmentMenuList = listOf(
        HomeFragment.newInstance(),
        FavoriteFragment.newInstance()
    )


    override fun onExtractIntents() {
    }

    override fun onReady() {

        val searchList = """"[{"awayTeamBadge":"https://www.thesportsdb.com/images/media/team/badge/yqtpsq1448806624.png","awayTeamName":"Napoli","dateEvent":"Wednesday, 18 Mar 2020","homeTeamBadge":"https://www.thesportsdb.com/images/media/team/badge/xqwpup1473502878.png","homeTeamName":"Barcelona","idAwayTeam":"133670","idEvent":"673276","idHomeTeam":"133739","idLeague":"4480","intRound":"8","stadium":"Camp Nou","strAwayTeam":"Napoli","strEvent":"Barcelona vs Napoli","strEventAlternate":"Napoli @ Barcelona","strFilename":"UEFA Champions League 2020-03-18 Barcelona vs Napoli","strHomeTeam":"Barcelona","strLeague":"UEFA Champions League","strLocked":"unlocked","strSeason":"1920","strSport":"Soccer","strTime":"20:00:00"},{"awayTeamBadge":"https://www.thesportsdb.com/images/media/team/badge/xqwpup1473502878.png","awayTeamName":"Barcelona","dateEvent":"Tuesday, 25 Feb 2020","homeTeamBadge":"https://www.thesportsdb.com/images/media/team/badge/yqtpsq1448806624.png","homeTeamName":"Napoli","idAwayTeam":"133739","idEvent":"672120","idHomeTeam":"133670","idLeague":"4480","intRound":"7","stadium":"Stadio San Paolo","strAwayTeam":"Barcelona","strEvent":"Napoli vs Barcelona","strEventAlternate":"Barcelona @ Napoli","strFilename":"UEFA Champions League 2020-02-25 Napoli vs Barcelona","strHomeTeam":"Napoli","strLeague":"UEFA Champions League","strLocked":"unlocked","strSeason":"1920","strSport":"Soccer","strTime":"20:00:00"}]""""
        log(searchList)
        val list = searchList.substring(1, searchList.length - 1)
        val newlist = list.fromJson<List<MatchResponse>>()
        log("NEWLIST $newlist")

        setupBottomNav()
    }

    override fun onBackPressed() {
        handleBackPressed()
    }

    fun setupBottomNav() {
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bnav_home -> selectFragment(mFragmentMenuList[0])
                R.id.bnav_favorite -> selectFragment(mFragmentMenuList[1])
            }
            return@setOnNavigationItemSelectedListener true
        }
        selectFragment(mFragmentMenuList[0])
    }

    fun selectFragment(fragment: Fragment) {

        val tag = when (fragment) {
            is HomeFragment -> HomeFragment::class.java.simpleName
            is FavoriteFragment -> FavoriteFragment::class.java.simpleName
            else -> ""
        }

        supportFragmentManager.beginTransaction().apply {
            if (supportFragmentManager.findFragmentByTag(tag) == null) {
                add(R.id.frameLayout, fragment, tag)
            }

            show(fragment)

            mFragmentMenuList.forEach {
                it.takeIf { it != fragment }?.let { fragmentToHide ->
                    hide(fragmentToHide)
                }
            }
        }.commit()
    }

    fun gotoSearchFragment() {
        supportFragmentManager.beginTransaction().apply {
            val searchFragment = SearchFragment.newInstance()
            if (!searchFragment.isAdded)
                add(R.id.frameSearch, searchFragment, SearchFragment::class.java.simpleName)
            else
                show(searchFragment)

            commit()
        }
    }

    fun handleBackPressed() {
        when {
            supportFragmentManager.findFragmentByTag(SearchFragment::class.java.simpleName) != null -> {
                supportFragmentManager.beginTransaction().apply {
                    remove(supportFragmentManager.findFragmentByTag(SearchFragment::class.java.simpleName)!!)
                    commit()
                }
            }
            bottomNav.selectedItemId != R.id.bnav_home -> bottomNav.selectedItemId = R.id.bnav_home
            bottomNav.selectedItemId == R.id.bnav_home -> checkIsDoublePressed()
            else -> checkIsDoublePressed()
        }
    }

    fun checkIsDoublePressed() {
        if (isDoubleBackPressed) {
            super.onBackPressed()
        } else {
            isDoubleBackPressed = true
            toastSpammable(getString(R.string.double_exit))
            GlobalScope.launch {
                delay(2000L)
                isDoubleBackPressed = false
            }
        }
    }
}
