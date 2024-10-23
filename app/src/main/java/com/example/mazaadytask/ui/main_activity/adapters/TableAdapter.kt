package com.example.mazaadytask.ui.main_activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaadytask.databinding.ItemTableBinding

class TableAdapter(private val selectedValues: List<Pair<String, String?>>) :
    RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(selectedValues[position])
    }

    override fun getItemCount(): Int = selectedValues.size

    inner class ViewHolder(private val binding: ItemTableBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<String, String?>) {
            binding.keyTextView.text = item.first
            binding.valueTextView.text = item.second ?: ""
        }
    }
}