package com.example.mazaadytask.ui.second_activity.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Course
import com.example.mazaadytask.databinding.CourseItemBinding

class CoursePagerAdapter() :
    RecyclerView.Adapter<CoursePagerAdapter.CourseViewHolder>() {

    private val courseList = mutableListOf<Course>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val binding = CourseItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courseList[position]
        holder.bind(course)
    }

    fun setData(courses: List<Course>) {
        courseList.clear()
        courseList.addAll(courses)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int = courseList.size

    inner class CourseViewHolder(private val binding: CourseItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(course: Course) {

        }
    }
}