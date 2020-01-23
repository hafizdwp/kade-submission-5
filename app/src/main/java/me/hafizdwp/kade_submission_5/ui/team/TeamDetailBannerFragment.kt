package me.hafizdwp.kade_submission_5.ui.team

import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.team_detail_banner_fragment.*
import me.hafizdwp.kade_submission_5.R
import me.hafizdwp.kade_submission_5.base.BaseFragment
import me.hafizdwp.kade_submission_5.utils.extentions.withArgs

/**
 * @author hafizdwp
 * 23/01/2020
 **/
class TeamDetailBannerFragment : BaseFragment<TeamDetailActivity>() {


    companion object {
        private const val ARGS_IMAGE_URL = "args"

        fun newInstance(url: String) = TeamDetailBannerFragment().withArgs {
            putString(ARGS_IMAGE_URL, url)
        }
    }

    override val layoutRes: Int
        get() = R.layout.team_detail_banner_fragment

    var mImageUrl = ""


    override fun onExtractArguments() {
        mImageUrl = arguments?.getString(ARGS_IMAGE_URL, "") ?: ""
    }

    override fun onReady() {
        Glide.with(this)
                .load(mImageUrl)
                .into(imgBanner)
    }
}