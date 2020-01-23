package me.hafizdwp.kade_submission_5.ui.home

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_fragment.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseFragment
import me.hafizdwp.kade_submission_5.data.model.LeagueData
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.MainActivity
import me.hafizdwp.kade_submission_5.ui.home.recentmatches.RecentMatchesAdapter
import me.hafizdwp.kade_submission_5.ui.home.upmatches.UpmatchesAdapter
import me.hafizdwp.kade_submission_5.ui.league.LeagueDetailActivity
import me.hafizdwp.kade_submission_5.ui.matches.MatchDetailActivity
import me.hafizdwp.kade_submission_5.utils.extentions.obtainViewModel
import me.hafizdwp.kade_submission_5.utils.extentions.withArgs

/**
 * @author hafizdwp
 * 15/12/2019
 **/
class HomeFragment : BaseFragment<MainActivity>(), HomeActionListener {

    companion object {
        fun newInstance() = HomeFragment().withArgs { }
    }

    override val layoutRes: Int
        get() = R.layout.home_fragment

    val mViewModel by lazy { obtainViewModel<HomeViewModel>() }
    var mLeaguesAdapter: HomeLeaguesAdapter? = null
    var mUpmatchesAdapter: UpmatchesAdapter? = null
    var mRecentAdapter: RecentMatchesAdapter? = null

    val listUpmatches = arrayListOf<MatchResponse>()
    val listRecentMatches = arrayListOf<MatchResponse>()


    override fun onExtractArguments() {
    }

    override fun onReady() {

        setupObserver()
        setupLeaguesRecycler()
        setupUpmatchesRecycler()
        setupRecentRecycler()

        // call
        mViewModel.getRecentMatches()
        mViewModel.getUpcomingMatches()

        // listener
        etSearch.setOnClickListener {
            childFragmentManager.beginTransaction().apply {
                (activity as MainActivity).gotoSearchFragment()
            }
        }
    }

    fun setupObserver() = mViewModel.apply {
        upmatchesLD.observe {
            it?.let {
                listUpmatches.clear()
                listUpmatches.addAll(it)
                mUpmatchesAdapter?.notifyDataSetChanged()
            }
        }

        recentLD.observe {
            it?.let {
                listRecentMatches.clear()
                listRecentMatches.addAll(it)
                mRecentAdapter?.notifyDataSetChanged()
            }
        }
    }

    fun setupRecentRecycler() {
        mRecentAdapter = RecentMatchesAdapter(
                items = listRecentMatches,
                listener = this@HomeFragment
        )

        recyclerRecent.apply {
            adapter = mRecentAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            itemAnimator = DefaultItemAnimator()
        }
    }

    fun setupLeaguesRecycler() {
        mLeaguesAdapter = HomeLeaguesAdapter(this@HomeFragment)

        recyclerLeagues.apply {
            adapter = mLeaguesAdapter
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun setupUpmatchesRecycler() {
        mUpmatchesAdapter = UpmatchesAdapter(
                items = listUpmatches,
                listener = this@HomeFragment
        )

        recyclerUpMatches.apply {
            adapter = mUpmatchesAdapter
            layoutManager = LinearLayoutManager(mContext)
            itemAnimator = DefaultItemAnimator()
        }
    }

    override fun onLeagueClick(data: LeagueData) {
        LeagueDetailActivity.startActivity(mContext, data.id)
    }

    override fun onMatchClick(data: MatchResponse) {
        MatchDetailActivity.startActivity(mContext, (data.idEvent ?: "0").toInt())
    }
}