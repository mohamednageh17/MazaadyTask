package com.example.mazaadytask.ui.main_activity.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.remote.response.property.Options
import com.example.domain.remote.response.property.Property
import com.example.mazaadytask.databinding.PropertyItemBinding
import com.example.mazaadytask.utilis.setAsBottomSheet
import com.example.mazaadytask.utilis.visible


class PropertiesAdapter(
    val addOtherOption: Boolean = true,
    val onPropertySelected: (Property?) -> Unit,
    val onOptionSelected: (Options?) -> Unit
) : RecyclerView.Adapter<PropertiesAdapter.DataViewHolder>() {

    private val data = ArrayList<Property?>()

    fun setData(list: List<Property?>) {
        data.clear()
        data.addAll(list.apply {
            if (addOtherOption) {
                this.forEach {
                    it?.options?.add(
                        0, Options(id = -1, name = "Other")
                    )
                }
            }
        })
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun updateChildOptions(item: Property, childProperties: List<Property>) {
        val position = data.indexOf(data.find { it?.id == item.id })
        item.childProperties = childProperties
        notifyItemChanged(position)
    }


    fun collectSelectedValues(): List<Pair<String, String?>> {
        val selectedValues = mutableListOf<Pair<String, String?>>()

        fun collectFromProperty(property: Property) {
            val key = property.name ?: ""
            val value = if (property.selectedOption?.id != -1)
                property.selectedOption?.name
            else
                property.otherValue

            selectedValues.add(Pair(key, value))

            property.childProperties.forEach { childProperty ->
                collectFromProperty(childProperty)
            }
        }

        data.forEach { property ->
            property?.let { collectFromProperty(it) }
        }

        return selectedValues
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            PropertyItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class DataViewHolder(private val binding: PropertyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var childAdapter: PropertiesAdapter? = null

        fun bind(item: Property?) {
            with(binding) {

                item?.let { item ->
                    if (item.selectOtherValue) {
                        customInput.visibility = VISIBLE
                        recyclerView.visibility = GONE
                    } else if (item.childProperties.isNotEmpty()) {
                        customInput.visibility = GONE
                        recyclerView.visibility = VISIBLE
                    }

                    input.hint = item.name
                    input.editText?.setText(item.selectedOption?.name)
                    customInput.editText?.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {

                        }

                        override fun afterTextChanged(s: Editable?) {
                            item.otherValue = s.toString()
                        }
                    })
                    input.setAsBottomSheet<Property>(context = root.context,
                        title = item.name ?: "",
                        list = item.options,
                        onItemSelectedListener = {
                            item.selectOtherValue = it?.id == -1
                            item.selectedOption = it
                            notifyItemChanged(adapterPosition)

                            onPropertySelected.invoke(item)
                            onOptionSelected.invoke(it)
                        })

                    if (item.childProperties.isNotEmpty()) {
                        binding.recyclerView.visible()
                        childAdapter = PropertiesAdapter(
                            addOtherOption = false,
                            onPropertySelected = {},
                            onOptionSelected = {}).apply {
                            setData(item.childProperties)
                        }
                        binding.recyclerView.adapter = childAdapter
                    } else {
                        binding.recyclerView.visibility = GONE
                    }

                }

            }

        }
    }
}
