package com.example.hrm_management.Views.Menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = menuAdapter


        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            // Start a coroutine within the activity's scope
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // Synchronize data using syncManager
                    syncManager.sync()

                    // Add a delay of, for example, 1 second
                    delay(1000) // Delay for 1 second (adjust the time as needed)

                    // Continue with UI updates after the delay
                    withContext(Dispatchers.Main) {
                        // Split the string into individual menu items
                        val menuItems = (getmenuitems(manager.getMenuList()))

                        // Update the RecyclerView adapter
                        refreshMenuAdapter(menuItems)

                        // Stop the refreshing animation
                        swipeRefreshLayout.isRefreshing = false
                    }
                } catch (e: Exception) {
                    // Handle exceptions here
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

    // You can call this function when you want to refresh the adapter with new data
    // For example, when you have updated data and want to refresh the RecyclerView:
    // refreshMenuAdapter(newData)
}