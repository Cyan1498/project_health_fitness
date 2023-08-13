package com.grupoc.project_health_fitness.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.grupoc.project_health_fitness.R

class AgregarAlimentoFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_agregar_alimento, container, false)
        val closeText = view.findViewById<TextView>(R.id.closeText)
        closeText.setOnClickListener {
            requireFragmentManager().beginTransaction().remove(this).commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_Añadir = view.findViewById<Button>(R.id.registrarAlimento_button)
        val edit_ingAlim = view.findViewById<EditText>(R.id.ingresarAlimento_txt)
        val edit_cal = view.findViewById<EditText>(R.id.ingresarCalorias_txt)
        val edit_gras = view.findViewById<EditText>(R.id.ingresarGrasa_txt)
        val edit_carb = view.findViewById<EditText>(R.id.ingresarCarbohidratos_txt)
        val edit_prot = view.findViewById<EditText>(R.id.ingresarProteina_txt)

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        btn_Añadir.setOnClickListener {
            // Verificar si los campos están vacíos

            val nombre = edit_ingAlim.text.toString().trim()
            val caloriasText = edit_cal.text.toString().trim()
            val grasaText = edit_gras.text.toString().trim()
            val carbohidratosText = edit_carb.text.toString().trim()
            val proteinaText = edit_prot.text.toString().trim()

            // Verificar si los campos están vacíos
            if (nombre.isEmpty() || caloriasText.isEmpty() || grasaText.isEmpty() ||
                carbohidratosText.isEmpty() || proteinaText.isEmpty()
            ) {
                // Mostrar mensaje de campos vacíos
                Toast.makeText(
                    requireContext(),
                    "Por favor, completa todos los campos.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val calorias = caloriasText.toDouble()
            val grasa = grasaText.toDouble()
            val carbohidratos = carbohidratosText.toDouble()
            val proteina = proteinaText.toDouble()

            val food = hashMapOf(
                "nombre" to nombre,
                "calorias" to calorias,
                "grasa" to grasa,
                "carbohidratos" to carbohidratos,
                "proteina" to proteina,
            )

            // Agregar el alimento a Firestore
            db.collection("food")
                .add(food)
                .addOnSuccessListener {
                    // Operación exitosa
                    Toast.makeText(
                        requireContext(),
                        "Alimento agregado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    edit_ingAlim.text.clear()
                    edit_cal.text.clear()
                    edit_gras.text.clear()
                    edit_carb.text.clear()
                    edit_prot.text.clear()
                    // Aquí puedes realizar cualquier otra acción que desees después de agregar el alimento
                }
                .addOnFailureListener { e ->
                    // Error al agregar el alimento
                    Toast.makeText(
                        requireContext(),
                        "Error al agregar el alimento: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }
    }


}
