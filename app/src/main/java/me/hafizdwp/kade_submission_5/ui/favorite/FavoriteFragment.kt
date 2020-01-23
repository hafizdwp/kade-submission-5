package me.hafizdwp.kade_submission_5.ui.favorite

import androidx.recyclerview.widget.LinearLayoutManager
import com.readystatesoftware.chuck.internal.ui.MainActivity
import kotlinx.android.synthetic.main.favorite_fragment.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseFragment
import me.hafizdwp.kade_submission_5.data.model.LeagueData
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.home.HomeActionListener
import me.hafizdwp.kade_submission_5.ui.home.upmatches.UpmatchesAdapter
import me.hafizdwp.kade_submission_5.ui.matches.MatchDetailActivity
import me.hafizdwp.kade_submission_5.utils.extentions.gone
import me.hafizdwp.kade_submission_5.utils.extentions.obtainViewModel
import me.hafizdwp.kade_submission_5.utils.extentions.visible
import me.hafizdwp.kade_submission_5.utils.extentions.withArgs

/**
 * @author hafizdwp
 * 15/12/2019
 **/
class FavoriteFragment : BaseFragment<MainActivity>(), HomeActionListener {

    companion object {
        fun newInstance() = FavoriteFragment().withArgs { }
    }

    override val layoutRes: Int
        get() = R.layout.favorite_fragment

    private val mViewModel by lazy { obtainViewModel<FavoriteViewModel>() }
    lateinit var mAdapter: UpmatchesAdapter
    var mListFavorite = arrayListOf<MatchResponse>()


    override fun onExtractArguments() {
    }

    override fun onReady() {

        setupObserver()

        mAdapter = UpmatchesAdapter(
                items = mListFavorite,
                listener = this
        )

        recyclerFavorite.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
        layoutEmpty.gone()
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getAllFavorite()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isHidden) {
            mViewModel.getAllFavorite()
        }
    }

    private fun setupObserver() = mViewModel.apply {
        matchLD.observe {
            if (it == null) {
                recyclerFavorite.gone()
                layoutEmpty.visible()
            } else {
                recyclerFavorite.visible()
                layoutEmpty.gone()
                mListFavorite.clear()
                mListFavorite.addAll(it)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onMatchClick(data: MatchResponse) {
        MatchDetailActivity.startActivity(mContext, (data.idEvent ?: "0").toInt())
    }

    override fun onLeagueClick(data: LeagueData) {
        // Not in use
    }
}