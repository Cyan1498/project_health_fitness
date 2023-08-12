package com.grupoc.project_health_fitness.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object ExitConfirmationDialog {

    fun show(context: Context, onConfirm: () -> Unit) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("¿Estás seguro de salir sin guardar cambios?")
            .setPositiveButton("Salir") { _, _ ->
                onConfirm.invoke()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
