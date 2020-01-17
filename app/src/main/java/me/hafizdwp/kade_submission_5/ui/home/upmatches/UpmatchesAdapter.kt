package me.hafizdwp.kade_submission_5.ui.home.upmatches

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.matches_item.view.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.home.HomeActionListener

/**
 * @author hafizdwp
 * 16/12/2019
 **/
class UpmatchesAdapter(val items: List<MatchResponse>,
                       val listener: HomeActionListener) : BaseRecyclerAdapter<MatchResponse>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.matches_item
    override val mListItem: List<MatchResponse>
        get() = items

    override fun onBind(itemView: View, model: MatchResponse, position: Int) {
        itemView.apply {
            textDate.text = model.dateEvent
            textStadium.text = model.stadium
            textHomeTeam.text = model.homeTeamName
            textAwayTeam.text = model.awayTeamName
            Glide.with(context)
                    .load(model.homeTeamBadge)
                    .into(imgBadgeLeft)
            Glide.with(context)
                    .load(model.awayTeamBadge)
                    .into(imgBadgeRight)
            textLeagueName.text = model.strLeague

            rootView.setOnClickListener {
                listener.onMatchClick(model)
            }
        }
    }
}