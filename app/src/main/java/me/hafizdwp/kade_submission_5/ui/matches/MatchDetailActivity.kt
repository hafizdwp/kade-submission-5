package me.hafizdwp.kade_submission_5.ui.matches

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.match_detail_activity.*
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseActivity
import me.hafizdwp.kade_submission_5.data.model.GnaData
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.obtainViewModel
import me.hafizdwp.kade_submission_5.utils.extentions.toastSpammable
import me.hafizdwp.kade_submission_5.utils.extentions.visible

/**
 * @author hafizdwp
 * 06/01/2020
 **/
class MatchDetailActivity : BaseActivity() {

    companion object {
        const val EXTRA_MATCH_ID = "ex_match_id"

        fun startActivity(context: Context,
                          matchId: Int) {
            context.startActivity(Intent(context, MatchDetailActivity::class.java).apply {
                putExtra(EXTRA_MATCH_ID, matchId)
            })
        }
    }

    override val layoutRes: Int
        get() = R.layout.match_detail_activity

    lateinit var mViewModel: MatchDetailViewModel
    lateinit var mAdapter: MatchDetailGnaAdapter


    override fun onExtractIntents() {
        mViewModel = obtainViewModel()
        mViewModel.mMatchId = intent.getIntExtra(EXTRA_MATCH_ID, 0)
    }

    override fun onReady() {
        setupObserver()

        imgBack.setOnClickListener {
            onBackPressed()
        }
        imgFavorite.isClickable = false
        imgFavorite.setOnClickListener {
            mViewModel.saveOrDeleteFavorite()
        }

        mViewModel.viewModelScope.launch {
            mViewModel.getDetailMatches()
        }
    }

    @SuppressLint("SetTextI18n")
    fun setupObserver() = mViewModel.apply {
        matchesLD.observe {
            when (it) {
                is ResultState.Success -> {
                    setupData(it.data)
                }
            }
        }

        goalsLD.observe { listGna ->

            // Notify adapter n recycler
            mAdapter = MatchDetailGnaAdapter(listGna ?: emptyList())
            recyclerGna.apply {
                adapter = mAdapter
                layoutManager = LinearLayoutManager(this@MatchDetailActivity)
            }

            // Count home n away goals
            var homeGoals = 0
            var awayGoals = 0
            listGna?.forEach { gna ->
                when (gna.goalType) {
                    GnaData.GoalType.HOME -> homeGoals++
                    GnaData.GoalType.AWAY -> awayGoals++
                }
            }

            if (shouldDisableGoalsLD.value == false)
                textScore.text = "$homeGoals  :  $awayGoals"
        }

        shouldDisableGoalsLD.observe {
            if (it == true) {
                textScore.text = "-"
                textGoalsStrip.visible()
            }
        }

        shouldInflateFavoriteButtonLD.observe {
            if (it == true)
                imgFavorite.setImageResource(R.drawable.ic_loved)
            else
                imgFavorite.setImageResource(R.drawable.ic_love)
        }

        globalToast.observe {
            toastSpammable(getString(it!!))
        }
    }

    private fun setupData(matchResponse: MatchResponse) = matchResponse.also {
        textTitle.text = it?.strEvent
        textDate.text = it?.dateEvent
        textStadium.text = it?.stadium
        Glide.with(this@MatchDetailActivity)
                .load(it?.homeTeamBadge)
                .into(imgBadgeLeft)
        Glide.with(this@MatchDetailActivity)
                .load(it?.awayTeamBadge)
                .into(imgBadgeRight)

        if (it?.isFavorited == true)
            imgFavorite.setImageResource(R.drawable.ic_loved)
        else
            imgFavorite.setImageResource(R.drawable.ic_love)
        imgFavorite.isClickable = true

        mViewModel.shouldDisableGoalsLD.value = matchResponse.intHomeScore == null
        mViewModel.goalsLD.value = mViewModel.filterGoals(matchResponse)
    }
}