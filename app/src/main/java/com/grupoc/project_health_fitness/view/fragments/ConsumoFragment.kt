package com.grupoc.project_health_fitness.view.fragments

import android.animation.LayoutTransition
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.card.MaterialCardView
import com.google.android.play.core.integrity.v
import com.grupoc.project_health_fitness.ConsumoAdapter
import com.grupoc.project_health_fitness.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class ConsumoFragment : Fragment(), ConsumoAdapter.OnItemClickListener {

    private var currentDate = Calendar.getInstance().time
    private var dayCounter: Int = 0
    private lateinit var viewPager2: ViewPager2
    private lateinit var dataList: MutableList<String>
    private lateinit var adapter: ConsumoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_consumo, container, false)

               // val dataList = listOf("Item 1", "Item 2", "Item 3")
       // val adapter = ConsumoAdapter(dataList,this)
       // viewPager2.adapter = adapter

       // val nextButton: Button = view.findViewById(R.id.next_button)
      //  nextButton.setOnClickListener {
          //  val currentItem = viewPager2.currentItem
          //  if (currentItem < adapter.itemCount - 1) {
          //      viewPager2.currentItem = currentItem + 1
          //  } else {
                // Si estás en la última página, puedes implementar algún comportamiento adicional aquí
           // }
        //}

        //val previousButton: Button = view.findViewById(R.id.previous_button)
       // previousButton.setOnClickListener {
        //    val currentItem = viewPager2.currentItem
        //    if (currentItem > 0) {
        //        viewPager2.currentItem = currentItem - 1
       //     } else {
                // Si estás en la primera página, puedes implementar algún comportamiento adicional aquí
        //    }
       // }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager2 = view.findViewById(R.id.viewPager2)
        dataList = mutableListOf("Item 1")
        adapter = ConsumoAdapter(dataList, this)
        viewPager2.adapter = adapter

        val hoyText: TextView = view.findViewById(R.id.hoy_text)
        val nextButton: Button = view.findViewById(R.id.next_button)
        val previousButton: Button = view.findViewById(R.id.previous_button)

        // Formatear la fecha en el formato deseado
        //  val dateFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
        //  val formattedDate = dateFormat.format(currentDate)

        // Establecer el texto del botón con la fecha actual formateada
        // hoyText.text = formattedDate

        updateDateText(hoyText)

        nextButton.setOnClickListener {
            dayCounter++
            updateDateText(hoyText)
            val newItem = "Item $dayCounter"
            dataList.add(newItem)
            viewPager2.currentItem = viewPager2.currentItem + 1
            adapter.notifyItemInserted(dataList.size - 1) // Notificar al adaptador que los datos han cambiado
        }

        previousButton.setOnClickListener {
            if (viewPager2.currentItem > 0) {
                viewPager2.currentItem = viewPager2.currentItem - 1
                dayCounter--
                updateDateText(hoyText)
            }
        }
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
       private fun updateDateText(hoyTextView: TextView) {
           val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
           val calendar = Calendar.getInstance()
           calendar.time = currentDate
           calendar.add(Calendar.DAY_OF_YEAR, dayCounter)
           val newDate = calendar.time
           hoyTextView.text = dateFormat.format(newDate)
       }


    }


