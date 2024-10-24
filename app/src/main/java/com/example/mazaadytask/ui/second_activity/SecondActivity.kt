package com.example.mazaadytask.ui.second_activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mazaadytask.R
import com.example.mazaadytask.databinding.SecondActivityBinding
import com.example.mazaadytask.ui.second_activity.adapters.CourseCategoryAdapter
import com.example.mazaadytask.ui.second_activity.adapters.PersonAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondActivity: AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding

    private val courseCategoryAdapter by lazy { CourseCategoryAdapter(onItemClick = {}) }
    private val personAdapter by lazy { PersonAdapter(onItemClick = {}) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.secondActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        initDummyData()
    }

    private fun initRecyclerView() {
        binding.apply {
            courseCategoryRecyclerView.adapter = courseCategoryAdapter
            personsRecyclerView.adapter = personAdapter
        }
    }

    private fun initDummyData(){
        courseCategoryAdapter.setData(Data.initCourseCategoriesData())
        personAdapter.setData(Data.initPersonsData(context = this))

    }

}