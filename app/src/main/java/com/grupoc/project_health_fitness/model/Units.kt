package com.grupoc.project_health_fitness.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Units (
    val id: String,
    val name: String,
    val description: String?,
): Parcelable {
    // Constructor sin argumentos requerido por Firebase Firestore
    constructor() : this(
        "",
        "",
        null
    )
}