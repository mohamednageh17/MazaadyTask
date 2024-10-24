package com.example.mazaadytask.ui.second_activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Person
import com.example.mazaadytask.databinding.PersonItemBinding

class PersonAdapter(
    val onItemClick: (Person?) -> Unit
) :
    RecyclerView.Adapter<PersonAdapter.DataViewHolder>() {

    private var dataSet: MutableList<Person> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            PersonItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun setData(list: MutableList<Person>) {
        dataSet.addAll(list)
        notifyDataSetChanged()
    }


    fun clear() {
        dataSet.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dataSet.size
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    inner class DataViewHolder(private val binding: PersonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Person?) {
            with(binding) {
                item?.let { item ->
                    personIV.setImageDrawable(item.image)
                }
                root.setOnClickListener {
                    onItemClick(item)
                }
            }

        }
    }
}