package com.example.mazaadytask.ui.second_activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.CourseCategory
import com.example.mazaadytask.R
import com.example.mazaadytask.databinding.CourseCategoryItemBinding

class CourseCategoryAdapter(
    val onItemClick: (CourseCategory?) -> Unit
) :
    RecyclerView.Adapter<CourseCategoryAdapter.DataViewHolder>() {

    private var dataSet: MutableList<CourseCategory> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            CourseCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    fun setData(list: MutableList<CourseCategory>) {
        dataSet.addAll(list.apply {
            firstOrNull()?.isSelected = true
        })
        notifyDataSetChanged()
    }

    fun selectItem(item: CourseCategory) {
        dataSet.forEach {
            it.isSelected = it.id==item.id
        }
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

    inner class DataViewHolder(private val binding: CourseCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CourseCategory?) {
            with(binding) {
                item?.let { item ->
                    titleTv.text = item.name

                    when(item.isSelected){
                        true->{
                            titleTv.background = itemView.context.getDrawable(R.drawable.selected_category_bg)
                            titleTv.setTextColor(root.context.getColor(R.color.white))
                        }
                        false->{
                            titleTv.background = itemView.context.getDrawable(R.drawable.unselected_category_bg)
                            titleTv.setTextColor(root.context.getColor(R.color.font_gray))
                        }
                    }

                }
                root.setOnClickListener {
                    item?.let{
                        selectItem(it)
                    }
                    onItemClick(item)
                }
            }

        }
    }
}