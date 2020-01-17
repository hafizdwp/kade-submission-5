package me.hafizdwp.kade_submission_5.ui.matches

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.gna_item_away.view.*
import kotlinx.android.synthetic.main.gna_item_home.view.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_5.data.model.GnaData

/**
 * @author hafizdwp
 * 06/01/2020
 **/
class MatchDetailGnaAdapter(val listItem: List<GnaData>) : BaseRecyclerAdapter<GnaData>() {

    override val bindItemLayoutRes: Int?
        get() = null
    override val mListItem: List<GnaData>
        get() = listItem


    private object Type {
        const val HOME = 0
        const val AWAY = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = when (viewType) {
            Type.HOME -> LayoutInflater.from(parent.context).inflate(R.layout.gna_item_home, parent, false).apply {
                tag = Type.HOME
            }
            Type.AWAY -> LayoutInflater.from(parent.context).inflate(R.layout.gna_item_away, parent, false).apply {
                tag = Type.AWAY
            }
            else -> throw Exception("There is an error")
        }

        return BaseViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return when (listItem[position].goalType) {
            GnaData.GoalType.HOME -> Type.HOME
            GnaData.GoalType.AWAY -> Type.AWAY
        }
    }

    override fun onBind(itemView: View, model: GnaData, position: Int) {
        when (itemView.tag) {
            Type.HOME -> bindHome(itemView, model)
            Type.AWAY -> bindAway(itemView, model)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindHome(itemView: View, model: GnaData) {
        itemView.apply {
            textMinute.text = model.minute + "'"
            textScorer.text = model.goalScorer
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindAway(itemView: View, model: GnaData) {
        itemView.apply {
            textAwayMinute.text = model.minute + "'"
            textAwayScorer.text = model.goalScorer
        }
    }
}