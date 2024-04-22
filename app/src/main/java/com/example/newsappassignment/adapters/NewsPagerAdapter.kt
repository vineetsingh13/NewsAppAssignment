package com.example.newsappassignment.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsappassignment.ui.businessFragment
import com.example.newsappassignment.ui.entertainmentFragment
import com.example.newsappassignment.ui.generalFragment
import com.example.newsappassignment.ui.headLinesFragment
import com.example.newsappassignment.ui.healthFragment
import com.example.newsappassignment.ui.scienceFragment
import com.example.newsappassignment.ui.sportsFragmet
import com.example.newsappassignment.ui.technologyFragment

class NewsPagerAdapter(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
    override fun getItemCount(): Int {
        return 8
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> headLinesFragment() // All Headlines section
            1 -> businessFragment()
            2 -> entertainmentFragment()
            3 -> generalFragment()
            4 -> healthFragment()
            5 -> scienceFragment()
            6 -> sportsFragmet()
            7 -> technologyFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }


}