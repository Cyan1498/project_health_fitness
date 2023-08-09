package com.grupoc.project_health_fitness.view.fragments.recordatorio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentRecordFormBinding
import com.grupoc.project_health_fitness.databinding.FragmentRecordListBinding
import com.grupoc.project_health_fitness.utils.anim.AnimationManager

class RecordListFragment : Fragment() {

    private var _binding: FragmentRecordListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordListBinding.inflate(inflater, container, false)

        setupBtnAdd()
        return binding.root
    }

    private fun setupBtnAdd() {
        binding.fabAddUser.setOnClickListener {
            binding.fabAddUser.isVisible = false
            AnimationManager.startCircleAnimationWithDelay(
                binding.circle,
                this,
                R.anim.circle_explosion_anim,
                1500,
                ::showRecordatorioForm
            )
        }
    }

    private fun showRecordatorioForm() {
        val navController = findNavController()
        navController.navigate(R.id.action_recordListFragment_to_recordFormFragment)
        binding.fabAddUser.isVisible = true
        binding.circle.isVisible = false
    }

    override fun onResume() {
        super.onResume()
        binding.fabAddUser.isVisible = true
        binding.circle.isVisible = false
    }

}