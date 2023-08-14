package com.grupoc.project_health_fitness.view.fragments.recordatorio

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentRecordFormBinding
import com.grupoc.project_health_fitness.databinding.FragmentRecordListBinding
import com.grupoc.project_health_fitness.model.Recordatorio
import com.grupoc.project_health_fitness.utils.SnackbarUtils
import com.grupoc.project_health_fitness.utils.anim.AnimationManager
import com.grupoc.project_health_fitness.utils.interfaces.BottomNavVisibilityListener
import com.grupoc.project_health_fitness.view.activities.InicioActivity
import com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter.OnRecordClickListener
import com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter.RecordAdapter
import com.grupoc.project_health_fitness.viewmodel.RecordViewModel

//import com.grupoc.project_health_fitness.view.fragments.recordatorio.RecordListFragmentDirections


class RecordListFragment : Fragment(), OnRecordClickListener {

    private var _binding: FragmentRecordListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecordViewModel by viewModels()
    private lateinit var recordAdapter: RecordAdapter
    private lateinit var loadingAnimationView: LottieAnimationView
//    private var bottomNavigationVisibilityListener: BottomNavVisibilityListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordListBinding.inflate(inflater, container, false)

        loadingAnimationView = binding.lottieLoading
        //Cargar la data al recyclerview
        setupRecyclerView()
        loadingAnimationView.visibility = View.VISIBLE
        observeRecordData()
        setupBtnAdd()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (activity as InicioActivity).setShowBackArrow(false)
        (activity as InicioActivity).setBottomNavigationVisible(true)
        (activity as InicioActivity).setDrawerEnabled(true)
//        setupBtnAdd()
//        observeRecordData()
//        binding.etFilter.addTextChangedListener { userFilter ->
//            val searchText = userFilter.toString().trim()
//            viewModel.filterRecordatorios(searchText)
//        }
        binding.etFilter.addTextChangedListener { userFilter ->
            val searchText = userFilter.toString().trim()
            viewModel.filterRecordatorios(searchText)

            val filteredList = viewModel.filteredRecordatorios.value
            if (filteredList.isNullOrEmpty()) {
                binding.tvNoResults.visibility = View.VISIBLE
            } else {
                binding.tvNoResults.visibility = View.GONE
            }
        }

    }

    private fun setupBtnAdd() {
        binding.fabShowRecord.setOnClickListener {
            binding.fabShowRecord.isVisible = false
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
        binding.fabShowRecord.isVisible = true
        binding.circle.isVisible = false

    }

    override fun onResume() {
        super.onResume()
        binding.fabShowRecord.isVisible = true
        binding.circle.isVisible = false

    }

    private fun setupRecyclerView() {
        recordAdapter = RecordAdapter(emptyList(), this) // Empty list initially
        //recyclerView.adapter (apply optimiza, para no hacer con cada uno)
        binding.rcView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recordAdapter
        }
    }

    //Listar todos los items del recyclerview
    private fun observeRecordData() {
        viewModel.getAllRecordatorios().observe(viewLifecycleOwner) { recordList ->
            // Aquí actualizarías el RecyclerView con los datos cargados
            //Poder ordenar los item
            val sortedList = recordList.sortedByDescending { it.createdAt }
            recordAdapter.updateData(sortedList)
//            recordAdapter.updateData(recordList)
            // y luego ocultarías la animación de carga
            loadingAnimationView.visibility = View.GONE
        }
        viewModel.filteredRecordatorios.observe(viewLifecycleOwner) { filteredRecordList ->
            val sortedList = filteredRecordList.sortedByDescending { it.createdAt }
            recordAdapter.updateData(sortedList)
//            loadingAnimationView.visibility = View.GONE
        }

    }

    override fun onRecordItemClick(record: Recordatorio) {
//        TODO("Not yet implemented")
        binding.etFilter.setText("")
        val bundle = Bundle()
        bundle.putParcelable("recordToEdit", record)

        findNavController().navigate(
            R.id.action_recordListFragment_to_recordFormFragment,
            bundle
        )
//        val action = RecordListFragmentDirections.actionRecordListFragmentToRecordFormFragment(
//            recordToEdit = record
//        )
//        findNavController().navigate(action)
    }

    override fun onRecordDelete(record: Recordatorio, pos:Int) {
//        TODO("Not yet implemented")
//        viewModel.deleteRecordatorio(record)
//        recordAdapter.notifyItemRemoved(pos)
        showDeleteConfirmationDialog(record)

//        notifyDelete()
//        observeRecordData()
    }
    private fun notifyDelete(){
        viewModel.recordatorioDeleted.observe(viewLifecycleOwner) { success ->
            if (success) {
                SnackbarUtils.showSnackbar(
                    this,
                    "Recordatorio Eliminado con éxito",
                    backgroundColor = SnackbarUtils.SnackbarColors.GREEN
                )
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun showDeleteConfirmationDialog(record: Recordatorio) {
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Eliminar Registro")
            .setMessage("¿Estás seguro de que deseas eliminar este registro?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                viewModel.deleteRecordatorio(record)
                notifyDelete()
                observeRecordData()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

}