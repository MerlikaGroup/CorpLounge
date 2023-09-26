package com.example.hrm_management.Views.Menu

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hrm_management.R
import com.example.hrm_management.Views.Menu.MenuDataClass.MenuItem

class MenuAdapter(private var menuItems: List<com.example.hrm_management.Views.Menu.MenuDataClass.MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuItem = menuItems[position]
        holder.menuNameTextView.text = menuItem.name
        holder.itemView.visibility = if (menuItem.isVisible) View.VISIBLE else View.GONE

        // Add click listeners or other actions for menu items here
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuNameTextView: TextView = itemView.findViewById(R.id.menuNameTextView)
        // Add references to other UI elements in the menu item layout here
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newMenuItems: List<MenuItem>) {
        menuItems = newMenuItems
        notifyDataSetChanged()
    }

}
