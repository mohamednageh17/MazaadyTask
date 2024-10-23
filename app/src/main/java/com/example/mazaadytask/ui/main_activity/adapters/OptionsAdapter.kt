package com.example.mazaadytask.ui.main_activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.remote.response.property.Options
import com.example.mazaadytask.databinding.OptionItemBinding

class OptionsAdapter(
    val data: List<Options?> = arrayListOf(),
    val onItemClick: (Options?) -> Unit
) :
    RecyclerView.Adapter<OptionsAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            OptionItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class DataViewHolder(private val binding: OptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {

        }

        fun bind(item: Options?) {
            with(binding) {
                item?.let { item ->
                    titleTV.text = item.name
                }
                root.setOnClickListener {
                    onItemClick(item)
                }
            }

        }
    }
}