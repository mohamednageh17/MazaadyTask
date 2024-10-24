package com.example.mazaadytask.ui.second_activity

import android.content.Context
import com.example.domain.model.CourseCategory
import com.example.domain.model.Person
import com.example.mazaadytask.R

object Data {
    fun initCourseCategoriesData(): MutableList<CourseCategory> {
        val data = mutableListOf<CourseCategory>()
        var item: CourseCategory
        for (i in 1..10) {
            item = CourseCategory(id = i, name = "Course $i")
            data.add(item)
        }
        return data
    }

    fun initPersonsData(context: Context): MutableList<Person> {
        val data = mutableListOf<Person>()
        var item: Person
        for (i in 1..10) {
            item = Person(name = "Person $i", image =context.getDrawable(R.drawable.person2) )
            data.add(item)
        }
        return data
    }
}