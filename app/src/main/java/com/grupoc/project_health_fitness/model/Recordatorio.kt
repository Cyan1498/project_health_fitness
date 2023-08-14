package com.grupoc.project_health_fitness.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recordatorio(
    val id: String?,
    val name: String,
//    val dateInit: Timestamp?,
    val dateInit: String?,
    val instruction: String?,
    val unit: String,
    val numDias: Int?,
    val note: String?,
    val createdAt: Timestamp? = null
) : Parcelable {
    // Constructor sin argumentos requerido por Firebase Firestore
    constructor() : this(
        null,
        "",
        null,
        null,
        "",
        null,
        null,
    )
}