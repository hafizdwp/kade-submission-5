package me.hafizdwp.kade_submission_5.ui.league

import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.clubrank_item.view.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_5.data.source.remote.responses.LeagueTableResponse

/**
 * @author hafizdwp
 * 23/12/2019
 **/
class ClubrankAdapter(val items: List<LeagueTableResponse>) : BaseRecyclerAdapter<LeagueTableResponse>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.clubrank_item
    override val mListItem: List<LeagueTableResponse>
        get() = items

    override fun onBind(itemView: View, model: LeagueTableResponse, position: Int) {
        itemView.apply {
            rootLayout.background = when (position) {
                0 -> ColorDrawable(ContextCompat.getColor(context, R.color.colorTertiary))
                else -> ColorDrawable(ContextCompat.getColor(context, R.color.white))
            }

            textRank.text = (position + 1).toString()
            Glide.with(context)
                    .load(model.badgeUrl)
                    .into(imgBadge)
            textClubName.text = model.name
            textPlayed.text = model.played.toString()
            textWin.text = model.win.toString()
            textLose.text = model.loss.toString()
            textDraw.text = model.draw.toString()
            textPoint.text = model.total.toString()
        }
    }
}