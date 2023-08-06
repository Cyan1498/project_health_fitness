package com.grupoc.project_health_fitness.utils

import android.content.res.ColorStateList
import androidx.fragment.app.Fragment
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import com.grupoc.project_health_fitness.R

object SnackbarUtils {

    // Definir los colores predeterminados 900
    object SnackbarColors {
        val GREEN = Color.GREEN
        val RED = Color.RED
        val WHITE = Color.WHITE
        // Puedes agregar más colores personalizados aquí
        val BLUE = Color.BLUE
        val YELLOW = Color.YELLOW
        val CUSTOM_COLOR = Color.parseColor("#EE5485")
        // ...
    }

    private const val defaultDuration = Snackbar.LENGTH_SHORT
    private val defaultTextColor = SnackbarColors.WHITE

    fun showSnackbar(
        fragment: Fragment,
        message: String,
        duration: Int = defaultDuration,
        actionText: String? = null,
        actionListener: View.OnClickListener? = null,
        backgroundColor: Int? = null,
        textColor: Int = defaultTextColor,
    ) {
        val snackbar = Snackbar.make(fragment.requireView(), message, duration)
        val snackbarView = snackbar.view

        // Personalizar el color de fondo del Snackbar
        if (backgroundColor != null) {
            snackbar.view.backgroundTintList = ColorStateList.valueOf(backgroundColor)
        }

        // Personalizar el color del texto del Snackbar
        snackbar.setTextColor(textColor)

        // Si se proporciona un botón de acción, establecerlo
        if (actionText != null && actionListener != null) {
            snackbar.setAction(actionText, actionListener)
        }

        // Aplicar el estilo con esquinas redondeadas
        snackbarView.setBackgroundResource(R.drawable.snackbar_bkg)

        // Configurar el ancho del Snackbar según el contenido del mensaje
        val parentParams = snackbarView.layoutParams as ViewGroup.MarginLayoutParams
        parentParams.width = ViewGroup.LayoutParams.WRAP_CONTENT

        // Centrar el Snackbar horizontalmente y ajustar el margen superior
        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER_HORIZONTAL
        params.topMargin = 180 // Valor fijo del margen superior en píxeles

        // Aplicar los parámetros actualizados al Snackbar
        snackbarView.layoutParams = params

        snackbar.show()
    }

}


