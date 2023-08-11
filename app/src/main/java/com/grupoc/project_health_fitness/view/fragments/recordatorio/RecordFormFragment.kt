package com.grupoc.project_health_fitness.view.fragments.recordatorio

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.Timestamp
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentRecordFormBinding
import com.grupoc.project_health_fitness.model.Recordatorio
import com.grupoc.project_health_fitness.utils.SnackbarUtils
import com.grupoc.project_health_fitness.utils.validations.RecordFormValidator
import com.grupoc.project_health_fitness.view.activities.InicioActivity
import com.grupoc.project_health_fitness.viewmodel.RecordViewModel
import java.util.Calendar
import java.util.Locale

class RecordFormFragment : Fragment() {

    private var _binding: FragmentRecordFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var loadingAnimationView: LottieAnimationView
    private val viewModel: RecordViewModel by viewModels()
    private lateinit var recordFormValidator: RecordFormValidator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecordFormBinding.inflate(inflater, container, false)

        setupValidationListeners()
        binding.dtPicker.setOnClickListener { onScheduledDate() }
        dropdownInstruction()
        dropdownUnits()
//        btnAddRecord()
//        val args: RecordFormFragmentArgs by navArgs()
//        val selectedRecord = args.recordToEdit
        senShowRecorData()
        addOfUpdateRecord()
//        observerData()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Resto de tu código

        (activity as InicioActivity).setShowBackArrow(true)
        (activity as InicioActivity).setBottomNavigationVisible(false)
        (activity as InicioActivity).setDrawerEnabled(false)
        observerData()
        // Resto de tu código
    }
    private fun onScheduledDate() {
        val selectedCalendar = Calendar.getInstance()
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)
        val dayOfMonth = selectedCalendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
            val formattedDay = if (d <= 9) "0$d" else "$d"
            val formattedMonth = if (m < 9) "0${m + 1}" else "${m + 1}"
            val formattedDate = "$formattedDay/$formattedMonth/$y"
            binding.dtPicker.setText(formattedDate)
        }
        DatePickerDialog(requireContext(), listener, year, month, dayOfMonth).show()
    }
    private fun dropdownInstruction() {
        val instruccion = resources.getStringArray(R.array.instrucciones)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, instruccion)
        binding.autoCompInstruction.setAdapter(arrayAdapter)
    }
    private fun dropdownUnits() {
//        val pastilla = resources.getStringArray(R.array.pastillas)
//        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, pastilla)
//        binding.autoCompPastilla.setAdapter(arrayAdapter)
        // Observar los cambios en la lista de unidades desde el ViewModel
        viewModel.getUnits().observe(viewLifecycleOwner) { units ->
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, units)
            binding.autoCompUnits.setAdapter(arrayAdapter)
        }
    }
    private fun setupValidationListeners() {
        recordFormValidator = RecordFormValidator(binding)

        binding.edName.doAfterTextChanged {
            recordFormValidator.validateName()
        }
        binding.dtPicker.doAfterTextChanged {
            recordFormValidator.validateDateInit()
        }
        binding.autoCompInstruction.doAfterTextChanged {
            recordFormValidator.validateInstruction()
        }
        binding.autoCompUnits.doAfterTextChanged {
            recordFormValidator.validateUnit()
        }
        binding.edNumdias.doAfterTextChanged {
            recordFormValidator.validateNumDias()
        }
        binding.edNote.doAfterTextChanged {
            recordFormValidator.validateNote()
        }
    }
    private fun addOfUpdateRecord() {
        binding.fabAddRecord.setOnClickListener {
            val nameUser = binding.edName.text.toString().trim()
            val dateInit = binding.dtPicker.text.toString().trim()
            val instruction = binding.autoCompInstruction.text.toString().trim()
            val unit = binding.autoCompUnits.text.toString().trim()
//            val numDias = binding.edNumdias.text.toString().trim()
            val numDiasStr = binding.edNumdias.text.toString().trim()
            val numDias = if (numDiasStr.isNotEmpty()) numDiasStr.toInt() else 0
            val note = binding.edNote.text.toString().trim()

            val formValidator = RecordFormValidator(binding)
            if (formValidator.validateForm()) {
                val selectedRecord = arguments?.getParcelable<Recordatorio>("recordToEdit")
//                loadingAnimationView.visibility = View.VISIBLE
//                loadingAnimationView.playAnimation()
                // Ocultar el formulario y mostrar la animación de carga
//                binding.formLayout.visibility = View.GONE
                val recordId = selectedRecord?.id
                if (recordId != null) {
                    viewModel.updateRecordatorio(recordId, nameUser, dateInit, instruction, unit, numDias, note)
                } else {
                    viewModel.registerRecordatorio(nameUser, dateInit, instruction, unit, numDias, note)
                }
            } else {
                SnackbarUtils.showSnackbar(
                    this,
                    "Complete correctamente sus datos",
                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
                )
            }
        }
    }
    private fun observerData() {
        viewModel.signupSuccess.observe(viewLifecycleOwner) { success ->
            //it representa el valor emitido por el LiveData observado.
            if (success) {
//                loadingAnimationView.visibility = View.GONE
                SnackbarUtils.showSnackbar(
                    this,
                    "Usuario registrado con éxito",
                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
                )
                //viewModel.resetEvents()
                // Cerrar el fragmento después de que la animación se haya detenido
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        viewModel.updateSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                SnackbarUtils.showSnackbar(
                    this,
                    "Usuario Actualizado con éxito",
                    backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
                )
                //viewModel.resetEvents()
                // Cerrar el fragmento después de que la animación se haya detenido
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        viewModel.unknownErrorEvent.observe(viewLifecycleOwner) {
            SnackbarUtils.showSnackbar(
                this,
                "Error al Registrar",
                backgroundColor = SnackbarUtils.SnackbarColors.CUSTOM_COLOR
            )
        }
    }
    private fun senShowRecorData(){
        val selectedRecord = arguments?.getParcelable<Recordatorio>("recordToEdit")
        if (selectedRecord != null) {
            showRecordData(selectedRecord)
        }

    }
    private fun showRecordData(selectedRecord: Recordatorio) {
        binding.edName.setText(selectedRecord.name)
        binding.dtPicker.setText(selectedRecord.dateInit)
        binding.autoCompInstruction.setText(selectedRecord.instruction)
        binding.autoCompUnits.setText(selectedRecord.unit)
        binding.edNumdias.setText(selectedRecord.numDias.toString())
        binding.edNote.setText(selectedRecord.note)
    }
}