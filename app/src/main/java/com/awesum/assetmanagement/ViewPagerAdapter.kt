package com.awesum.assetmanagement

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.awesum.assetmanagement.ui.asset.AssetManagementFragment
import com.awesum.assetmanagement.ui.reflection.ReflectionFragment

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AssetManagementFragment()
            1 -> ReflectionFragment()
            else -> AssetManagementFragment()
        }
    }
}
