package com.grupoc.project_health_fitness.utils

import android.content.res.ColorStateList
import androidx.fragment.app.Fragment
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar

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
        textColor: Int = defaultTextColor
    ) {
        val snackbar = Snackbar.make(fragment.requireView(), message, duration)

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

        snackbar.show()
    }

}


