package me.hafizdwp.kade_submission_5.ui.search

import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.team_item.view.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_5.data.source.remote.responses.TeamResponse
import me.hafizdwp.kade_submission_5.ui.home.HomeActionListener

/**
 * @author hafizdwp
 * 17/01/2020
 **/
class SearchTeamAdapter(val items: List<TeamResponse>,
                        val listener: SearchActionListener) : BaseRecyclerAdapter<TeamResponse>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.team_item
    override val mListItem: List<TeamResponse>
        get() = items

    override fun onBind(itemView: View, model: TeamResponse, position: Int) {
        itemView.apply {
            Glide.with(context)
                .load(model.strTeamBadge)
                .into(imgView)

            textName.text = model.strTeam

            rootView.setOnClickListener {
                listener.onTeamClick(model)
            }
        }
    }
}