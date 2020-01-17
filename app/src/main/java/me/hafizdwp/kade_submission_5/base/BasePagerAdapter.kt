package me.hafizdwp.kade_submission_5.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * @author hafizdwp
 * 30/11/2019
 **/
open class BasePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val listFragment = arrayListOf<Fragment>()
    val listTitle = arrayListOf<String>()

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (listTitle.isNotEmpty())
            listTitle[position]
        else
            null
    }

    fun addFragment(fragment: Fragment,
                    title: String? = null) {
        listFragment.add(fragment)
        title?.let {
            listTitle.add(title)
        }
    }
}