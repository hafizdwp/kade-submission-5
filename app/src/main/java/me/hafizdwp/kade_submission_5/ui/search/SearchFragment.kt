package me.hafizdwp.kade_submission_5.ui.search

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseFragment
import me.hafizdwp.kade_submission_5.data.model.LeagueData
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.MainActivity
import me.hafizdwp.kade_submission_5.ui.home.HomeActionListener
import me.hafizdwp.kade_submission_5.ui.matches.MatchDetailActivity
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.extentions.obtainViewModel
import me.hafizdwp.kade_submission_5.utils.extentions.toastSpammable
import me.hafizdwp.kade_submission_5.utils.extentions.withArgs


/**
 * @author hafizdwp
 * 07/01/2020
 **/
class SearchFragment : BaseFragment(), HomeActionListener {

    companion object {
        fun newInstance() = SearchFragment().withArgs { }
    }

    private var searchJob: Job? = null
    private val SEARCH_DELAY = 1500L

    override val layoutRes: Int
        get() = R.layout.search_fragment

    private val mViewModel: SearchViewModel by lazy { obtainViewModel<SearchViewModel>() }
    lateinit var mAdapter: SearchAdapter
    val mListMatches = arrayListOf<MatchResponse>()


    override fun onExtractArguments() {
    }

    override fun onReady() {

        etSearch.requestFocus()
        try {
            val imm = (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        } catch (e: Exception) {
        }

        myProgressView.build {
            bigText = getString(R.string.search_match)
            labelText = getString(R.string.search_label)
            icon = R.drawable.ic_ball
        }

        setupObserver()

        // setup Recycler
        mAdapter = SearchAdapter(
                items = mListMatches,
                listener = this@SearchFragment
        )

        recyclerSearch.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(mContext)
            itemAnimator = DefaultItemAnimator()
        }

        // add search listener to search's editText
        etSearch.doOnTextChanged { text, start, count, after ->
            searchJob?.cancel()
            searchJob = null
            searchJob = mViewModel.viewModelScope.launch {
                delay(SEARCH_DELAY)

                mViewModel.searchMatch(text.toString())
            }
        }
    }

    private fun setupObserver() = mViewModel.apply {
        matchesLD.observe {
            when(it) {
                is ResultState.Loading -> {
                    myProgressView.start()
                }

                is ResultState.Success -> {
                    myProgressView.stopAndGone()
                    mListMatches.clear()
                    mListMatches.addAll(it.data)
                    mAdapter.notifyDataSetChanged()
                }

                is ResultState.Error -> {
                    myProgressView.stopAndGone()
                    toastSpammable(it.error)
                }
            }
        }
    }

    /**
     * HomeActionListener implementations
     * ---------------------------------------------------------------------------------------------
     * */
    override fun onMatchClick(data: MatchResponse) {
        MatchDetailActivity.startActivity(mContext, (data.idEvent ?: "0").toInt())
    }

    override fun onLeagueClick(data: LeagueData) {
        // Not in use
    }
}