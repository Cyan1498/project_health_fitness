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
import java.util.Date
import java.util.Locale


class ConsumoFragment : Fragment(), ConsumoAdapter.OnItemClickListener {

    private var currentDate = Calendar.getInstance().time
    private var dayCounter: Int = 0
    private lateinit var viewPager2: ViewPager2
    private lateinit var dataList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_consumo, container, false)

        viewPager2 = view.findViewById(R.id.viewPager2)

        dataList = generateDataList()
        val adapter = ConsumoAdapter(dataList, this)
        viewPager2.adapter = adapter

        val hoyText: TextView = view.findViewById(R.id.hoy_text)
        val nextButton: Button = view.findViewById(R.id.next_button)
        val previousButton: Button = view.findViewById(R.id.previous_button)

        updateDateText(hoyText)

        nextButton.setOnClickListener {
            dayCounter++
            updateDateText(hoyText)
            dataList.add("Item $dayCounter")
            viewPager2.currentItem = viewPager2.currentItem + 1
            adapter.notifyDataSetChanged()
        }

        previousButton.setOnClickListener {
            if (viewPager2.currentItem > 0) {
                viewPager2.currentItem = viewPager2.currentItem - 1
                dayCounter--
                updateDateText(hoyText)
            }
        }

        return view
    }

    private fun generateDataList(): MutableList<String> {
        val dataList = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // Agregar el primer elemento con la fecha actual
        dataList.add(dateFormat.format(currentDate))

        // Agregar los elementos posteriores con incremento de días
        for (i in 1 until dataList.size) {
            calendar.time = currentDate
            calendar.add(Calendar.DAY_OF_YEAR, i)
            dataList.add(dateFormat.format(calendar.time))
        }

        return dataList
    }

    private fun updateDateText(hoyTextView: TextView) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DAY_OF_YEAR, dayCounter)
        val newDate = calendar.time
        hoyTextView.text = dateFormat.format(newDate)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onAlimentacionButtonClick() {
        findNavController().navigate(R.id.alimentacionFragment)
    }

    private fun generateDateList(startDate: Date, days: Int): List<Date> {
        val dateList = mutableListOf<Date>()
        val calendar = Calendar.getInstance()

        calendar.time = startDate
        for (i in 0 until days) {
            dateList.add(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return dateList
    }

    private fun findInitialPageIndex(currentDate: Date, dateList: List<Date>): Int {
        for ((index, date) in dateList.withIndex()) {
            if (isSameDay(currentDate, date)) {
                return index
            }
        }
        return 0
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(date1) == sdf.format(date2)
    }

}


