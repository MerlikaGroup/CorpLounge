package com.example.hrm_management.Utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.example.hrm_management.R
import com.example.hrm_management.Views.Menu.TaskPhase


object LoadingDialog {
    private var loadingDialog: Dialog? = null

    fun showLoading(context: Context, phase: TaskPhase) {
        // Create the dialog if it doesn't exist
        if (loadingDialog == null) {
            loadingDialog = Dialog(context, R.style.CustomDialogTheme)
            loadingDialog?.setContentView(R.layout.layout_loading)
            loadingDialog?.setCancelable(false)
        }

        // Get references to UI elements in the custom loading layout
        val progressBar = loadingDialog?.findViewById<ProgressBar>(R.id.progressBar2)
        val loadingText = loadingDialog?.findViewById<TextView>(R.id.loadingText)

        // Update UI elements based on the task phase
        when (phase) {
            TaskPhase.INITIALIZING -> {
                loadingText?.text = "Initializing..."
                progressBar?.progress = 0
            }
            TaskPhase.LOADING_DATA -> {
                loadingText?.text = "Loading data..."
                progressBar?.progress = 40
            }
            TaskPhase.SYNCING_DATA -> {
                loadingText?.text = "Syncing data..."
                progressBar?.progress = 80
            }
            TaskPhase.FINALIZING -> {
                loadingText?.text = "Finalizing..."
                progressBar?.progress = 100
            }
        }

        // Show the custom dialog
        loadingDialog?.show()
        progressBar?.visibility = View.VISIBLE
    }

    fun dismissLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    // Use this method to attach the observer to an activity's lifecycle

}