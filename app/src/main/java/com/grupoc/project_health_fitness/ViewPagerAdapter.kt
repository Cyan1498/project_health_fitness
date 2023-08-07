package com.grupoc.project_health_fitness

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.grupoc.project_health_fitness.view.fragments.LoginFragment
import com.grupoc.project_health_fitness.view.fragments.RegistroFragment

class ViewPagerAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            RegistroFragment()
        } else {
            LoginFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }

}