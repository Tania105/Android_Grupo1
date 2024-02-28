package com.example.miincidencia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miincidencia.R

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val notifications = mutableListOf<String>()
    private val selectedItems = mutableSetOf<Int>()

    fun addNotification(notification: String) {
        notifications.add(notification)
        notifyDataSetChanged()
    }

    fun getSelectedNotifications(): List<String> {
        return selectedItems.map { notifications[it] }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notificacion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]
        holder.checkBox.text = notification
        holder.checkBox.isChecked = selectedItems.contains(position)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedItems.add(position)
            } else {
                selectedItems.remove(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    fun deleteSelectedNotifications() {
        selectedItems.sortedDescending().forEach {
            notifications.removeAt(it)
            notifyItemRemoved(it)
        }
        selectedItems.clear()
    }
    fun toggleSelection(position: Int) {
        if (selectedItems.contains(position)) {
            selectedItems.remove(position)
        } else {
            selectedItems.add(position)
        }
        notifyItemChanged(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        private val contenidoNotificacion: TextView = itemView.findViewById(R.id.contenidoNotificacion)

        fun bind(position: Int) {
            val notification = notifications[position]
            contenidoNotificacion.text = notification
            checkBox.isChecked = selectedItems.contains(position)
            checkBox.setOnClickListener {
                toggleSelection(adapterPosition)
            }
    }
    }



}