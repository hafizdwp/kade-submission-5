package me.hafizdwp.kade_submission_5.utils.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.utils.extentions.gone
import me.hafizdwp.kade_submission_5.utils.extentions.visible

/**
 * @author hafizdwp
 * 01/12/2019
 **/
class MyProgressView(internal var context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var layoutRoot: RelativeLayout
    private var progressBar: ProgressBar
    private var textTitle: TextView
    private var textLabel: TextView
    private var imgTooltip: ImageView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_myprogress, null).apply {
            layoutRoot = findViewById(R.id.layoutRoot)
            progressBar = findViewById(R.id.progressBar)
            textTitle = findViewById(R.id.textTitle)
            textLabel = findViewById(R.id.textLabel)
            imgTooltip = findViewById(R.id.imgTooltip)
        }

        addView(view)
    }

    var bigText: String = "-"
    var labelText: String = "-"
    var icon: Int = 0

    fun build(builder: MyProgressView.() -> Unit) {
        builder().apply {
            textTitle.text = bigText
            textLabel.text = labelText
            try {
                imgTooltip.setImageResource(icon)
            } catch (e: Exception) {
            }
        }
    }

    fun start() {
        this.visible()
        progressBar.visible()
        textTitle.gone()
        textLabel.gone()
        imgTooltip.gone()
    }

    fun stopAndGone() {
        this.gone()
    }
}