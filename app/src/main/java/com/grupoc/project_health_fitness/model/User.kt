package com.grupoc.project_health_fitness.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String?,
    val name: String?,
    val email: String?,
    val password: String?,
    val weight: String?,
    val height: String?,
    val createdAt: Timestamp? = null
) : Parcelable {
    // Constructor sin argumentos requerido por Firebase Firestore
    constructor() : this(null, null, null, null, null, null)
}
