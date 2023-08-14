package com.grupoc.project_health_fitness.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fitness(
    var id: String? = null,
    var category: String? = null,
    var reps: String? = null,
    var rest: String? = null,
    var series: String? = null
): Parcelable{
    constructor(): this(
        null,
        null,
        null,
        null,
        null
    )
}