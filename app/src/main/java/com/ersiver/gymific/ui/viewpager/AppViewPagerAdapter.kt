package com.ersiver.gymific.ui.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ersiver.gymific.ui.arms.ArmsFragment
import com.ersiver.gymific.ui.cardio.CardioFragment
import com.ersiver.gymific.ui.legs.LegsFragment
import timber.log.Timber

const val CARDIO_PAGE_INDEX = 0
const val LEGS_PAGE_INDEX = 1
const val ARMS_PAGE_INDEX = 2

class AppViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    init {
        Timber.i("AppViewPagerAdapter init")
    }

    override fun getItemCount() = tabFragmentsCreator.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreator : Map<Int, ()->Fragment> = mapOf(
        CARDIO_PAGE_INDEX to {CardioFragment()},
        LEGS_PAGE_INDEX to {LegsFragment()},
        ARMS_PAGE_INDEX to{ArmsFragment()}
    )
}