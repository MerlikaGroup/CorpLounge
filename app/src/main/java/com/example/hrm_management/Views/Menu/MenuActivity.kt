package com.example.hrm_management.Views.Menu

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.hrm_management.AppModule.SharedPreferencesManager
import com.example.hrm_management.R
import com.example.hrm_management.Utils.SyncManager
import com.example.hrm_management.Views.Menu.MenuDataClass.MenuItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class MenuActivity : AppCompatActivity() {
    @Inject
    lateinit var manager: SharedPreferencesManager

    @Inject
    lateinit var syncManager: SyncManager

    private var loadingDialog: Dialog? = null // Declare the loading dialog as a global variable


    // Declare the adapter as a class-level variable
    private lateinit var menuAdapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Assuming you have the configuration data as a string
        val configurationString = manager.getMenuList()

        // Split the string into individual menu items
        val initialmenuitems = getmenuitems(configurationString)
        // Initialize the adapter and set it to the RecyclerView
        menuAdapter = MenuAdapter(initialmenuitems)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        recyclerView.adapter = menuAdapter


        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            // Start a coroutine within the activity's scope
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Display the loading dialog for the INITIALIZING phase
                    withContext(Dispatchers.Main) {
                        showLoading(TaskPhase.INITIALIZING)
                    }

                    // Perform initialization tasks (if any)
                    // ...

                    // Display the loading dialog for the LOADING_DATA phase
                    withContext(Dispatchers.Main) {
                        showLoading(TaskPhase.LOADING_DATA)
                    }

                    // Load data (if any)
                    // ...

                    // Display the loading dialog for the SYNCING_DATA phase
                    withContext(Dispatchers.Main) {
                        showLoading(TaskPhase.SYNCING_DATA)
                    }

                    // Synchronize data using syncManager
                    syncManager.sync()

                    // Display the loading dialog for the FINALIZING phase
                    withContext(Dispatchers.Main) {
                        showLoading(TaskPhase.FINALIZING)
                    }

                    // Simulate a delay (e.g., 1 second) to show the completed phase
                    delay(1000) // Delay for 1 second (adjust the time as needed)

                    // Continue with UI updates after the delay
                    withContext(Dispatchers.Main) {
                        // Split the string into individual menu items
                        val menuItems = getmenuitems(manager.getMenuList())

                        // Update the RecyclerView adapter
                        refreshMenuAdapter(menuItems)

                        // Stop the refreshing animation
                        swipeRefreshLayout.isRefreshing = false

                        // Dismiss the loading dialog after the task is complete
                        loadingDialog?.dismiss()
                    }
                } catch (e: Exception) {
                    // Handle exceptions here

                    Log.d("error", "error")
                }
            }
        }

    }
    // Create a function to refresh the adapter when needed
    fun refreshMenuAdapter(newData: List<MenuItem>) {
        menuAdapter.updateData(newData)
        menuAdapter.notifyDataSetChanged()
    }

    fun getmenuitems(menuItem: String): List<MenuItem> {
        // Split the input string into individual menu items and map them to MenuItem objects
        return menuItem.split(", ").map { menuItem ->
            val parts = menuItem.split(": ")
            if (parts.size == 2) {
                val menuName = parts[0].trim()
                val isVisible = parts[1].trim().toBoolean()
                MenuItem(menuName, isVisible)
            } else {
                MenuItem("", false)
            }
        }
    }

    private fun showLoading(phase: TaskPhase) {
        // Create the dialog if it doesn't exist
        if (loadingDialog == null) {
            loadingDialog = Dialog(this, R.style.CustomDialogTheme)
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
        progressBar?.visibility = View.VISIBLE;
    }

    // You can call this function when you want to refresh the adapter with new data
    // For example, when you have updated data and want to refresh the RecyclerView:
    // refreshMenuAdapter(newData)
}