package me.hafizdwp.kade_submission_5.ui.team

import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.match_detail_activity.imgBack
import kotlinx.android.synthetic.main.match_detail_activity.imgFavorite
import kotlinx.android.synthetic.main.match_detail_activity.textTitle
import kotlinx.android.synthetic.main.team_detail_activity.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseActivity
import me.hafizdwp.kade_submission_5.base.BasePagerAdapter
import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.disableClickTablayout
import me.hafizdwp.kade_submission_5.utils.extentions.obtainViewModel

/**
 * @author hafizdwp
 * 23/01/2020
 **/
class TeamDetailActivity : BaseActivity() {

    companion object {
        private const val EXTRA_TEAM_ID = "extra_team_id"

        fun startActivity(context: Context,
                          teamId: Int) {
            context.startActivity(Intent(context, TeamDetailActivity::class.java).apply {
                putExtra(EXTRA_TEAM_ID, teamId)
            })
        }
    }

    override val layoutRes: Int
        get() = R.layout.team_detail_activity

    lateinit var mViewModel: TeamDetailViewModel
    lateinit var mPagerAdapter: BasePagerAdapter

    override fun onExtractIntents() {
        mViewModel = obtainViewModel()
        mViewModel.teamId = intent.getIntExtra(EXTRA_TEAM_ID, 0)
    }

    override fun onReady() {
        setupObserver()

        imgBack.setOnClickListener {
            onBackPressed()
        }
        imgFavorite.isClickable = false
        imgFavorite.setOnClickListener {
//            mViewModel.saveOrDeleteFavorite()
        }

        mPagerAdapter = BasePagerAdapter(supportFragmentManager)

        mViewModel.getTeamDetail()
    }

    private fun setupObserver() = mViewModel.apply {
        teamDetailsLD.observe {
            when (it) {
                is ResultState.Success -> {
                    setupData(it.data[0])
                }
            }
        }
    }

    private fun setupData(data: TeamResponse) = data.run {
        textTitle.text = strTeam
        val listFanart = listOf(
                strTeamFanart1,
                strTeamFanart2,
                strTeamFanart3,
                strTeamFanart4,
                strTeamBadge
        )

        // setup viewPager
        listFanart.forEach {
            if (!it.isNullOrEmpty()) {
                mPagerAdapter.addFragment(
                        TeamDetailBannerFragment.newInstance(it)
                )
            }
        }

        vpSlider.adapter = mPagerAdapter
        tabSlider.setupWithViewPager(vpSlider, true)
        tabSlider.disableClickTablayout()

        Glide.with(this@TeamDetailActivity)
                .load(strTeamBadge)
                .into(imgBadge)

        textName.text = strTeam
        textStory.text = strDescriptionEN
    }
}