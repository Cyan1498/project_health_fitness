package com.grupoc.project_health_fitness.view.fragments.recordatorio.adapter

import com.grupoc.project_health_fitness.model.Recordatorio

interface OnRecordClickListener {
    fun onRecordItemClick(record: Recordatorio)
    fun onRecordDelete(record: Recordatorio, pos:Int)
}
