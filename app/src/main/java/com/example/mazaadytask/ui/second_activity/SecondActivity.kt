package com.example.mazaadytask.ui.second_activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.mazaadytask.R
import com.example.mazaadytask.databinding.SecondActivityBinding
import com.example.mazaadytask.ui.second_activity.adapters.CourseCategoryAdapter
import com.example.mazaadytask.ui.second_activity.adapters.CoursePagerAdapter
import com.example.mazaadytask.ui.second_activity.adapters.PersonAdapter
import com.example.mazaadytask.utilis.HorizontalMarginItemDecoration
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs


@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding

    private val courseCategoryAdapter by lazy { CourseCategoryAdapter(onItemClick = {}) }
    private val personAdapter by lazy { PersonAdapter(onItemClick = {}) }
    private val courseAdapter by lazy { CoursePagerAdapter() }

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
        initViewPager()
        initDummyData()
    }

    private fun initRecyclerView() {
        binding.apply {
            courseCategoryRecyclerView.adapter = courseCategoryAdapter
            personsRecyclerView.adapter = personAdapter
        }
    }

    private fun initViewPager() {
        with(binding) {
            viewPager.adapter = courseAdapter
            viewPager.offscreenPageLimit = 1


            val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx =
                resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                page.translationX = -pageTranslationX * position
                // Next line scales the item's height. You can remove it if you don't want this effect
                page.scaleY = 1 - (0.25f * abs(position))
                // If you want a fading effect uncomment the next line:
                // page.alpha = 0.25f + (1 - abs(position))
            }
            viewPager.setPageTransformer(pageTransformer)

            val itemDecoration = HorizontalMarginItemDecoration(
                this@SecondActivity,
                R.dimen.viewpager_current_item_horizontal_margin
            )
            viewPager.addItemDecoration(itemDecoration)

        }
    }

    private fun initDummyData() {
        courseCategoryAdapter.setData(Data.initCourseCategoriesData())
        personAdapter.setData(Data.initPersonsData(context = this))
        courseAdapter.setData(Data.initCoursesData())
    }

}