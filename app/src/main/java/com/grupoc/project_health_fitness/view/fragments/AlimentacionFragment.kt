package com.grupoc.project_health_fitness.view.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.google.firebase.firestore.FirebaseFirestore
import com.grupoc.project_health_fitness.R
import com.grupoc.project_health_fitness.databinding.FragmentAlimentacionBinding
import java.util.Calendar


class AlimentacionFragment : Fragment() {

    private lateinit var binding: FragmentAlimentacionBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var autoCompleteAdapter: ArrayAdapter<String>
    private val alimentosList = ArrayList<String>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlimentacionBinding.inflate(inflater, container, false)


        binding.dtpickerAlimentacion.setOnClickListener { onScheduledDate() }
        binding.timePickerAlimentacion.setOnClickListener { onScheduledTime() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAlimentacionBinding.bind(view)
        db = FirebaseFirestore.getInstance()

        setUpAutoCompleteTextView()

        val añadir: AppCompatImageButton = view.findViewById(R.id.añadirAlimentacion_button)
        añadir.setOnClickListener {
            val showAgregarAlimento = AgregarAlimentoFragment()
            showAgregarAlimento.show(
                (activity as AppCompatActivity).supportFragmentManager,
                "showAgregarAlimento"
            )
        }

        binding.fabAddRecord.setOnClickListener {
            guardarRegistro()
        }
    }

    private fun setUpAutoCompleteTextView() {
        val autoCompInstruction = binding.autoCompNomComida
        val alimentosList = ArrayList<String>()
        autoCompleteAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, alimentosList)
        autoCompInstruction.setAdapter(autoCompleteAdapter)

        autoCompInstruction.setOnItemClickListener { _, _, position, _ ->
            val selectedAlimento = autoCompleteAdapter.getItem(position)
            if (selectedAlimento != null) {
                mostrarDatosAlimento(selectedAlimento)
            }
        }

        obtenerNombresAlimentos()

        // Configura el ArrayAdapter para el AutoCompleteTextView de tipo de comida
        val tipoComidaOptions = arrayOf("Aperitivo", "Desayuno", "Almuerzo", "Cena")
        val tipoComidaAdapter =
            ArrayAdapter(requireContext(), R.layout.dropdown_item, tipoComidaOptions)
        val autoCompTipoComida = binding.autoCompTipComida
        autoCompTipoComida.setAdapter(tipoComidaAdapter)

        autoCompTipoComida.setOnItemClickListener { _, _, position, _ ->
            val selectedTipoComida = tipoComidaOptions[position]
            // Realiza las acciones que desees con el tipo de comida seleccionado
        }
    }


    private fun obtenerNombresAlimentos() {
        db.collection("food")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Toast.makeText(
                        requireContext(),
                        "Error al obtener alimentos: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@addSnapshotListener
                }

                alimentosList.clear()

                for (document in snapshot!!) {
                    val nombre = document["nombre"] as? String
                    nombre?.let {
                        alimentosList.add(nombre)
                    }
                }

                autoCompleteAdapter.notifyDataSetChanged()
            }
        autoCompleteAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            alimentosList
        )

        binding.autoCompNomComida.setAdapter(autoCompleteAdapter)
        binding.autoCompNomComida.setOnItemClickListener { parent, _, position, _ ->
            val selectedAlimento = parent.getItemAtPosition(position) as String
            mostrarDatosAlimento(selectedAlimento)
        }
    }

    private fun mostrarDatosAlimento(alimentoNombre: String) {
        db.collection("food")
            .whereEqualTo("nombre", alimentoNombre)
            .get()
            .addOnSuccessListener { result ->
                if (!result.isEmpty) {
                    val document = result.documents[0]
                    val calorias = document.getDouble("calorias")
                    val grasa = document.getDouble("grasa")
                    val carbohidratos = document.getDouble("carbohidratos")
                    val proteina = document.getDouble("proteina")

                    binding.alimentacionCalorias.text = calorias?.toString() ?: "N/A"
                    binding.alimentacionGrasa.text = grasa?.toString() ?: "N/A"
                    binding.alimentacionCarbohidrato.text = carbohidratos?.toString() ?: "N/A"
                    binding.alimentacionProteina.text = proteina?.toString() ?: "N/A"
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "Error al obtener datos del alimento: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun guardarRegistro() {
        // Obtén los datos ingresados por el usuario
        val alimento = binding.autoCompNomComida.text.toString().trim()
        val tipoComida = binding.autoCompTipComida.text.toString().trim()
        val calorias = binding.alimentacionCalorias.text.toString().toDouble()
        val grasa = binding.alimentacionGrasa.text.toString().toDouble()
        val carbohidrato = binding.alimentacionCarbohidrato.text.toString().toDouble()
        val proteina = binding.alimentacionProteina.text.toString().toDouble()
        val dia = binding.dtpickerAlimentacion.text.toString().trim()
        val hora = binding.timePickerAlimentacion.text.toString().trim()

        // Obtén la fecha y hora seleccionadas (debe ser implementado por ti)

        // Crea un objeto Map con los datos
        val feeding = hashMapOf(
            "alimento" to alimento,
            "tipoComida" to tipoComida,
            "calorias" to calorias,
            "grasa" to grasa,
            "carbohidrato" to carbohidrato,
            "proteina" to proteina,
            "dia" to dia,
            "hora" to hora,

        )

        if (alimento.isEmpty() || tipoComida.isEmpty() || dia.isEmpty() ||
            hora.isEmpty()) {
            // Mostrar mensaje de campos vacíos
            Toast.makeText(
                requireContext(),
                "Por favor, completa todos los campos.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }


        // Agrega el registro a Firebase Firestore
        db.collection("feeding")
            .add(feeding)
            .addOnSuccessListener {
                // Registro agregado exitosamente
                Toast.makeText(
                    requireContext(),
                    "Registro de alimentación guardado",
                    Toast.LENGTH_SHORT
                ).show()

                binding.autoCompNomComida.text = null
                binding.autoCompTipComida.text = null
                binding.dtpickerAlimentacion.text = null
                binding.timePickerAlimentacion.text = null
                // Limpiar campos o realizar otras acciones después de guardar
                // Restablecer los TextView de calorías, grasas, etc. a cadenas vacías
                binding.alimentacionCalorias.text = ""
                binding.alimentacionGrasa.text = ""
                binding.alimentacionCarbohidrato.text = ""
                binding.alimentacionProteina.text = ""

            }
            .addOnFailureListener { e ->
                // Error al agregar el registro
                Toast.makeText(
                    requireContext(),
                    "Error al guardar el registro: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun onScheduledDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val listener = DatePickerDialog.OnDateSetListener { datepicker, y, m, d ->
            val formattedDay = if (d <= 9) "0$d" else "$d"
            val formattedMonth = if (m < 9) "0${m + 1}" else "${m + 1}"
            val formattedDate = "$formattedDay/$formattedMonth/$y"

            binding.dtpickerAlimentacion.setText(formattedDate)

        }
        DatePickerDialog(requireContext(), listener, year, month, day).show()
    }

    private fun onScheduledTime(){
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val listener = TimePickerDialog.OnTimeSetListener { timePicker, h, m ->
            val formattedHour = if (h <= 9) "0$h" else "$h"
            val formattedMinute = if (m <= 9) "0$m" else "$m"
            val formattedTime = "$formattedHour:$formattedMinute"

            binding.timePickerAlimentacion.setText(formattedTime)
        }

        TimePickerDialog(requireContext(), listener, hour, minute, true).show()
    }
}
