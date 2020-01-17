package me.hafizdwp.kade_submission_5.ui.home

import android.view.View
import kotlinx.android.synthetic.main.home_leagues_item.view.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_5.data.model.LeagueData

/**
 * @author hafizdwp
 * 15/12/2019
 **/
class HomeLeaguesAdapter(val listener: HomeActionListener) : BaseRecyclerAdapter<LeagueData>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.home_leagues_item
    override val mListItem: List<LeagueData>
        get() = LeagueData.getAll()

    override fun onBind(itemView: View, model: LeagueData, position: Int) {
        itemView.apply {
            imgLogo.setImageResource(model.imgRes)
            textName.text = model.name
            rootView.setOnClickListener {
                listener.onLeagueClick(model)
            }
        }
    }
}