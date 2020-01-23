package me.hafizdwp.kade_submission_5.ui.search

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseFragment
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse
import me.hafizdwp.kade_submission_5.ui.MainActivity
import me.hafizdwp.kade_submission_5.ui.matches.MatchDetailActivity
import me.hafizdwp.kade_submission_5.ui.team.TeamDetailActivity
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.log
import me.hafizdwp.kade_submission_5.utils.extentions.obtainViewModel
import me.hafizdwp.kade_submission_5.utils.extentions.toastSpammable
import me.hafizdwp.kade_submission_5.utils.extentions.visible
import me.hafizdwp.kade_submission_5.utils.extentions.withArgs


/**
 * @author hafizdwp
 * 07/01/2020
 **/
class SearchFragment : BaseFragment<MainActivity>(), SearchActionListener {

    companion object {
        fun newInstance() = SearchFragment().withArgs { }
    }

    private var searchJob: Job? = null
    private val SEARCH_DELAY = 1500L

    override val layoutRes: Int
        get() = R.layout.search_fragment

    private val mViewModel: SearchViewModel by lazy { obtainViewModel<SearchViewModel>() }
    private lateinit var mAdapter: SearchAdapter
    private lateinit var mTeamAdapter: SearchTeamAdapter
    private val mListMatches = arrayListOf<MatchResponse>()
    private val mListTeams = arrayListOf<TeamResponse>()

    override fun onExtractArguments() {
    }

    override fun onReady() {

        etSearch.requestFocus()
        try {
            val imm = (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        } catch (e: Exception) {
        }

        // Show placeholder first to the UI
        myProgressView.build {
            bigText = getString(R.string.search_match)
            labelText = getString(R.string.search_label)
            icon = R.drawable.ic_ball
        }
        myProgressView.visible()

        setupObserver()
        setupMatchAdapter()
        setupTeamAdapter()

        // add search listener to search's editText
        etSearch.doOnTextChanged { text, start, count, after ->
            searchJob?.cancel()
            searchJob = null
            searchJob = mViewModel.viewModelScope.launch {
                delay(SEARCH_DELAY)

                mViewModel.searchMatch(text.toString())
                mViewModel.searchTeams(text.toString())
            }
        }

        imgBack.setOnClickListener {
            mActivity.onBackPressed()
        }
    }

    private fun setupMatchAdapter() {
        mAdapter = SearchAdapter(
                items = mListMatches,
                listener = this@SearchFragment
        )

        recyclerSearch.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(mContext)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupTeamAdapter() {
        mTeamAdapter = SearchTeamAdapter(
                items = mListTeams,
                listener = this@SearchFragment
        )

        recyclerTeam.apply {
            adapter = mTeamAdapter
            layoutManager = GridLayoutManager(mContext, 2)
            itemAnimator = DefaultItemAnimator()
        }
    }

    private fun setupObserver() = mViewModel.apply {
        matchesLD.observe {
            when (it) {
                is ResultState.Loading -> {
                    mListMatches.clear()
                    mAdapter.notifyDataSetChanged()
                    mListTeams.clear()
                    mTeamAdapter.notifyDataSetChanged()

                    myProgressView.start()
                    textResult.startAnimatingDots()
                }

                is ResultState.Success -> {
                    myProgressView.stopAndGone()
                    mListMatches.clear()
                    mListMatches.addAll(it.data)
                    mAdapter.notifyDataSetChanged()

                    updateMatchCount(it.data.size)
                }

                is ResultState.Error -> {
                    myProgressView.stopAndGone()
                    textResult.stopAnimatingDots(isError = true)
                    toastSpammable(it.error)
                }
            }
        }

        teamsLD.observe {
            when (it) {
                is ResultState.Success -> {
                    mListTeams.clear()
                    mListTeams.addAll(it.data)
                    mTeamAdapter.notifyDataSetChanged()

                    updateTeamCount(it.data.size)
                }

                is ResultState.Error -> {
                    myProgressView.stopAndGone()
                    textResult.stopAnimatingDots(isError = true)
                    toastSpammable(it.error)
                }
            }
        }

        resetResultLD.observe {

        }

        resultTextLD.observe {
            textResult.stopAnimatingDots()
            textResult.text = it
        }
    }

    private var mDotsJob: Job? = null
    private fun TextView.startAnimatingDots() {
        mDotsJob = CoroutineScope(Dispatchers.Main).launch {
            text = "."
            while (true) {
                log("result: $text")
                text = when {
                    text == ". . ." -> "."
                    else -> {
                        "$text ."
                    }
                }
                delay(222)
            }
        }
    }

    private fun TextView.stopAnimatingDots(isError: Boolean = false) {
        mDotsJob?.cancel()
        if (isError)
            text = "-"
    }


    /**
     * HomeActionListener implementations
     * ---------------------------------------------------------------------------------------------
     * */
    override fun onMatchClick(data: MatchResponse) {
        MatchDetailActivity.startActivity(mContext, (data.idEvent ?: "0").toInt())
    }

    override fun onTeamClick(data: TeamResponse) {
        TeamDetailActivity.startActivity(mContext, (data.idTeam ?: "0").toInt())
    }
}