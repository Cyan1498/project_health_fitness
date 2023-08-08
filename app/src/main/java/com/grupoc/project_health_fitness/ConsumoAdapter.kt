package com.grupoc.project_health_fitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.grupoc.project_health_fitness.view.fragments.ConsumoAguaFragment

class ConsumoAdapter(private val dataList: List<String>, private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ConsumoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val consumoCard: CardView = itemView.findViewById(R.id.consumo_cardView)
        val consumoAguaButton: Button = itemView.findViewById(R.id.consumoAgua_button)
        val alimentacionButton: Button = itemView.findViewById(R.id.alimentacion_button)

        init {
            alimentacionButton.setOnClickListener {
                onItemClickListener.onAlimentacionButtonClick()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_consumo, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// Configurar el clic del botón consumoAguaButton
        holder.consumoAguaButton.setOnClickListener {
            val showConsumoAgua = ConsumoAguaFragment()
            showConsumoAgua.show(
                (holder.itemView.context as AppCompatActivity).supportFragmentManager,
                "showAgregarAlimento"
            )
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClickListener {
        fun onAlimentacionButtonClick()
    }

}
