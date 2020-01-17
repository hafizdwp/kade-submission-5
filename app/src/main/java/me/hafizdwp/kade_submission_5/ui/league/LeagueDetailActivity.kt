package me.hafizdwp.kade_submission_5.ui.league

import android.content.Context
import android.content.Intent
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.league_detail_activity.*
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseActivity
import me.hafizdwp.kade_submission_5.data.model.LeagueData
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueDetailResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueTableResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.home.recentmatches.RecentMatchesAdapter
import me.hafizdwp.kade_submission_5.ui.home.upmatches.UpmatchesAdapter
import me.hafizdwp.kade_submission_5.ui.matches.MatchDetailActivity
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.gone
import me.hafizdwp.kade_submission_5.utils.extentions.obtainViewModel
import me.hafizdwp.kade_submission_5.utils.extentions.visible
import me.hafizdwp.kade_submission_5.utils.extentions.withLoadingPlaceholder

/**
 * @author hafizdwp
 * 19/12/2019
 **/
class LeagueDetailActivity : BaseActivity(), LeagueDetailActionListener {

    companion object {
        private const val EXTRA_LEAGUE_ID = "league_id"

        fun startActivity(context: Context,
                          leagueId: Int) {
            context.startActivity(Intent(context, LeagueDetailActivity::class.java).apply {
                putExtra(EXTRA_LEAGUE_ID, leagueId)
            })
        }
    }

    override val layoutRes: Int
        get() = R.layout.league_detail_activity

    lateinit var mViewModel: LeagueDetailViewModel
    lateinit var mClubrankAdapter: ClubrankAdapter
    lateinit var mUpmatchesAdapter: UpmatchesAdapter
    lateinit var mRecentAdapter: RecentMatchesAdapter

    var listRecentMatches = arrayListOf<MatchResponse>()
    var listUpmatchesData = arrayListOf<MatchResponse>()
    var listTables = arrayListOf<LeagueTableResponse>()


    override fun onExtractIntents() {
        mViewModel = obtainViewModel()
        mViewModel.mLeagueId = intent.getIntExtra(EXTRA_LEAGUE_ID, 0)
    }

    override fun onReady() {

        imgBack.setOnClickListener {
            onBackPressed()
        }

        setupObserver()

        //
        // Setup Recent Adapter
        //
        mRecentAdapter = RecentMatchesAdapter(
                items = listRecentMatches,
                listener = this
        )

        recyclerRecent.apply {
            adapter = mRecentAdapter
            layoutManager = LinearLayoutManager(this@LeagueDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        //
        // Setup Upmatches Adapter
        //
        mUpmatchesAdapter = UpmatchesAdapter(
                items = listUpmatchesData,
                listener = this@LeagueDetailActivity
        )

        recyclerUpMatches.apply {
            adapter = mUpmatchesAdapter
            layoutManager = LinearLayoutManager(this@LeagueDetailActivity)
            itemAnimator = DefaultItemAnimator()
        }

        //
        // Setup Clubrank Adapter
        //
        mClubrankAdapter = ClubrankAdapter(
                items = listTables
        )

        recyclerClubRank.apply {
            adapter = mClubrankAdapter
            layoutManager = LinearLayoutManager(this@LeagueDetailActivity)
            itemAnimator = DefaultItemAnimator()
        }


        // Call APIs
        mViewModel.viewModelScope.launch {
            mViewModel.getLeagueDetail()
            mViewModel.getRecentMatches()
            mViewModel.getUpmatches()
            mViewModel.getLeagueTables()
        }
    }

    fun setupObserver() = mViewModel.apply {
        detailLeagueLD.observe {
            when (it) {
                is ResultState.Success -> {
                    setupHeader(it.data)
                }
            }
        }

        recentMatchesLD.observe {
            when (it) {
                is ResultState.Success -> {
                    listRecentMatches.clear()
                    listRecentMatches.addAll(it.data)
                    mRecentAdapter.notifyDataSetChanged()
                }
            }
        }

        upmatchesLD.observe {
            when (it) {
                is ResultState.Success -> {
                    listUpmatchesData.clear()
                    listUpmatchesData.addAll(it.data)
                    mUpmatchesAdapter.notifyDataSetChanged()
                }
            }
        }

        tablesLD.observe {
            it?.let {
                listTables.clear()
                listTables.addAll(it)
                mClubrankAdapter.notifyDataSetChanged()
            }
        }

        clubrankProgressLD.observe {
            if (it == true)
                progressClubrank.visible()
            else
                progressClubrank.gone()
        }
    }

    fun setupHeader(data: LeagueDetailResponse?) {
        data?.let {
            Glide.with(this)
                    .load(it.strBadge)
                    .withLoadingPlaceholder(this)
                    .into(imgLeagueBadge)

            textLeagueName.text = it.strLeague
            textCountry.text = it.strCountry
        }
    }

    /**
     * LeagueDetailActionListener implementations
     * ---------------------------------------------------------------------------------------------
     * */

    override fun onMatchClick(data: MatchResponse) {
        MatchDetailActivity.startActivity(this, (data.idEvent ?: "0").toInt())

    }

    override fun onLeagueClick(data: LeagueData) {
        // Not in use
    }
}