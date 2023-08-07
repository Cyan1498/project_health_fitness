package com.grupoc.project_health_fitness.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.grupoc.project_health_fitness.R

class ProgressDialogCustom(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_dialog)
    }
}
