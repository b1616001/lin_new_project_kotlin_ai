package com.example.lin_new_project_kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.lin_new_project_kotlin.adapter.ViewPagerAdapter
import com.example.lin_new_project_kotlin.databinding.FragmentViewPagerBinding
import com.example.lin_new_project_kotlin.view.viewpager.BlackView
import com.example.lin_new_project_kotlin.view.viewpager.BlueView
import com.example.lin_new_project_kotlin.view.viewpager.RedView
import com.example.lin_new_project_kotlin.view.viewpager.ViewPagerView
import com.example.lin_new_project_kotlin.viewBinding.BaseBindingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener


class ViewPagerFragment : BaseBindingFragment<FragmentViewPagerBinding?>() {
    private var tabLayout: TabLayout? = null
    private var viewpager: ViewPager? = null
    private var arrayList: ArrayList<ViewPagerView<*>?>? = null
    private var redView: RedView? = null
    private var blueView: BlueView? = null
    private var blackView: BlackView? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    override fun onCreateViewBinding(layoutInflater: LayoutInflater): FragmentViewPagerBinding {
        return FragmentViewPagerBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    private fun initView() {
        redView = RedView(requireContext())
        blueView = BlueView(requireContext())
        blackView = BlackView(requireContext())
        arrayList = ArrayList<ViewPagerView<*>?>()
        arrayList!!.add(redView)
        arrayList!!.add(blueView)
        arrayList!!.add(blackView)
        viewPagerAdapter = ViewPagerAdapter(arrayList!!)
        tabLayout = getBinding()!!.tabLayout
        viewpager = getBinding()!!.viewPager
        tabLayout!!.addTab(tabLayout!!.newTab().setText("紅色"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("藍色"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("黑色"))
        viewpager!!.setAdapter(viewPagerAdapter)
        viewpager!!.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout!!.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewpager))

        viewpager!!.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                setLog("onPageScrolled:" + position)
            }

            override fun onPageSelected(position: Int) {
                setLog("onPageSelected:" + position)
                for (i in arrayList!!.indices) {
                    if (i == position) {
                        arrayList!!.get(i)!!.initData()
                    } else {
                        arrayList!!.get(i)!!.dismiss()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                setLog("onPageScrollStateChanged:" + state)
            }
        })
    }
}