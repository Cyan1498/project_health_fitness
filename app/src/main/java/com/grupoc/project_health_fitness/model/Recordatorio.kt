package com.grupoc.project_health_fitness.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recordatorio(
    val id: String?,
    val name: String?,
    val date_ini: Timestamp?,
    val instruction: String?,
    val unit: String?,
    val date_fin: Int?,
    val note: String?
) : Parcelable {
    // Constructor sin argumentos requerido por Firebase Firestore
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}