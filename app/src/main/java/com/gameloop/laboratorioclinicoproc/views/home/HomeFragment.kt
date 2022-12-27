package com.gameloop.laboratorioclinicoproc.views.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.gameloop.laboratorioclinicoproc.R
import com.gameloop.laboratorioclinicoproc.databinding.FragmentHomeBinding
import com.gameloop.laboratorioclinicoproc.views.laboratorytests.TestsFragment
import com.gameloop.laboratorioclinicoproc.views.patients.MyPatientsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setUpViewPager()
        setUpTabLayout()

        return binding.root
    }

    /**
     * Sets the [ViewPager2]'s adapter and adds a listener to [TabLayout] tab changes
     */
    private fun setUpViewPager() {
        binding.homePager.adapter = PageAdapter(requireActivity())
        binding.homeTabLayout.addOnTabSelectedListener(object: OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    binding.homePager.currentItem = tab.position
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    /**
     * Sets the [TabLayout] to listen to [ViewPager2] page changes and update the selected tab
     */
    private fun setUpTabLayout() {
        binding.homePager.registerOnPageChangeCallback( object: OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.homeTabLayout.selectTab(binding.homeTabLayout.getTabAt(position))
            }
        })
    }

    /**
     * A pager showing the [MyPatientsFragment] and [TestsFragment]
     */
    private inner class PageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        private val pages = 2

        override fun getItemCount(): Int = pages

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> MyPatientsFragment()
                1 -> TestsFragment()
                else -> throw IllegalArgumentException("Unknown page position")
            }
        }

    }
}