package me.hafizdwp.kade_submission_5.ui.home.recentmatches

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recentmatches_item.view.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_5.data.source.remote.responses.MatchResponse
import me.hafizdwp.kade_submission_5.ui.home.HomeActionListener

/**
 * @author hafizdwp
 * 07/01/2020
 **/
class RecentMatchesAdapter(val items: List<MatchResponse>,
                           val listener: HomeActionListener) : BaseRecyclerAdapter<MatchResponse>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.recentmatches_item
    override val mListItem: List<MatchResponse>
        get() = items

    @SuppressLint("SetTextI18n")
    override fun onBind(itemView: View, model: MatchResponse, position: Int) {
        itemView.apply {
            textDate.text = model.dateEvent
            textStadium.text = model.stadium
            textHomeName.text = model.homeTeamName
            textAwayName.text = model.awayTeamName
            Glide.with(context)
                    .load(model.homeTeamBadge)
                    .into(imgBadgeLeft)
            Glide.with(context)
                    .load(model.awayTeamBadge)
                    .into(imgBadgeRight)
            textLeagueName.text = model.strLeague
            textScore.text = "${model.intHomeScore}    :    ${model.intAwayScore}"

            rootView.setOnClickListener {
                listener.onMatchClick(model)
            }
        }
    }
}