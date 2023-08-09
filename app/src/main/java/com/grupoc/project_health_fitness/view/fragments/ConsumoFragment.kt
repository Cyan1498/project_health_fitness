package com.grupoc.project_health_fitness.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.grupoc.project_health_fitness.ConsumoAdapter
import com.grupoc.project_health_fitness.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ConsumoFragment : Fragment(), ConsumoAdapter.OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_consumo, container, false)
        val viewPager2: ViewPager2 = view.findViewById(R.id.viewPager2)
        val dataList = listOf("Item 1", "Item 2", "Item 3")
        val adapter = ConsumoAdapter(dataList,this)
        viewPager2.adapter = adapter

        val nextButton: Button = view.findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            val currentItem = viewPager2.currentItem
            if (currentItem < adapter.itemCount - 1) {
                viewPager2.currentItem = currentItem + 1
            } else {
                // Si estás en la última página, puedes implementar algún comportamiento adicional aquí
            }
        }

        val previousButton: Button = view.findViewById(R.id.previous_button)
        previousButton.setOnClickListener {
            val currentItem = viewPager2.currentItem
            if (currentItem > 0) {
                viewPager2.currentItem = currentItem - 1
            } else {
                // Si estás en la primera página, puedes implementar algún comportamiento adicional aquí
            }
        }
    

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

     //   val onConsumoAgua: Button = view.findViewById(R.id.consumoAgua_button)
      //  val onAlimentacion: Button = view.findViewById(R.id.alimentacion_button)

      //  onConsumoAgua.setOnClickListener {
      //      val showConsumoAgua = ConsumoAguaFragment()
       //     showConsumoAgua.show(
         //       (activity as AppCompatActivity).supportFragmentManager, "showAgregarAlimento"

        val hoyButton: Button = view.findViewById(R.id.hoy_text)

        // Obtener la fecha actual
        val currentDate = Calendar.getInstance().time

        // Formatear la fecha en el formato deseado
        val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
        val formattedDate = dateFormat.format(currentDate)

        // Establecer el texto del botón con la fecha actual formateada
        hoyButton.text = formattedDate

        }

    override fun onAlimentacionButtonClick() {
        findNavController().navigate(R.id.alimentacionFragment)
    }

// onAlimentacion.setOnClickListener {
        //    Log.d("ConsumoFragment", "Botón de Alimentación presionado")
        //    val showAlimentacion = AlimentacionFragment()
        ////   fragmentTransaction.replace(R.id.consumoFragment, showAlimentacion)
        //   fragmentTransaction.addToBackStack(null)
        //  fragmentTransaction.commit()
        // }

       // onAlimentacion.setOnClickListener {
        //    findNavController().navigate(R.id.alimentacionFragment)
       // }

    }
