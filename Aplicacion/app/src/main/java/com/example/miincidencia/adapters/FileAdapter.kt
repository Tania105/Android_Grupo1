package com.example.miincidencia

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class FileAdapter(private val fileNames: MutableList<String>) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {

    inner class FileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileNameTextView: TextView = itemView.findViewById(R.id.txtTituloAdjunto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_adjunto, parent, false)
        return FileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileName = fileNames[position]
        // Mostrar el nombre del archivo en el TextView
        holder.fileNameTextView.text = obtenerNombreArchivo(fileName)
    }

    override fun getItemCount() = fileNames.size

    // Método para obtener el nombre del archivo de la ruta de archivo
    private fun obtenerNombreArchivo(filePath: String): String {
        val file = File(filePath)
        return file.name
    }

    fun getFiles(): List<String> {
        return fileNames
    }

    fun addFilePath(filePath: String) {
        fileNames.add(filePath)
        notifyDataSetChanged()
    }
}