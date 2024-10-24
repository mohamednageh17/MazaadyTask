package com.example.mazaadytask.ui.main_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.domain.remote.response.category.Categories
import com.example.domain.remote.response.category.Children
import com.example.mazaadytask.R
import com.example.mazaadytask.base.MainViewState
import com.example.mazaadytask.databinding.ActivityMainBinding
import com.example.mazaadytask.ui.main_activity.adapters.PropertiesAdapter
import com.example.mazaadytask.ui.second_activity.SecondActivity
import com.example.mazaadytask.utilis.gone
import com.example.mazaadytask.utilis.setAsSpinner
import com.example.mazaadytask.utilis.showTableDialog
import com.example.mazaadytask.utilis.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

const val TAG = "MazaadyLog"


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    private val adapter by lazy {
        PropertiesAdapter(
            onPropertySelected = { property ->
                viewModel.selectedProperty=property
            },
            onOptionSelected = {option->
                option?.let {
                    if(it.child==true) {
                        it.id?.let { optionId ->
                            viewModel.getOptionsChild(optionId)
                        }
                    }
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRecyclerView()
        collect()
        setupController()
    }

    private fun setupController(){
        with(binding){
            submitBtn.setOnClickListener{
                showTableDialog(adapter.collectSelectedValues())
                adapter.collectSelectedValues().forEach {
                    Log.d("nageh",  it.first+"->"+it.second)
                }
            }

            goToSecondScreenBtn.setOnClickListener{
                startActivity(Intent(this@MainActivity, SecondActivity::class.java))
            }
        }
    }

    private fun initCategories(data: List<Categories>) {
        binding.inputCategory.setAsSpinner(
            binding.categorySpinner,
            data,
        ) { category ->
            adapter.clear()
            initSubCategories(category?.children ?: emptyList())
        }
    }

    private fun initSubCategories(data: List<Children>) {
        binding.inputSubCategory.setAsSpinner(
            binding.subCategorySpinner,
            data,
        ) { subCategory ->
            subCategory?.id?.let {
                viewModel.getProperties(it)
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun collect() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    MainViewState.Idle -> {

                    }

                    MainViewState.Loading -> {
                        binding.progressBar.visible()
                    }

                    is MainViewState.Success -> {
                        binding.progressBar.gone()
                        state.categories?.let { categories ->
                            initCategories(categories)
                        }
                        state.properties?.let { properties ->
                            adapter.setData(properties)
                        }
                        state.optionsChild?.let { optionsChild ->
                            viewModel.selectedProperty?.let {
                                adapter.updateChildOptions(it,optionsChild)
                            }
                        }

                    }

                    is MainViewState.Error -> {
                        binding.progressBar.gone()
                        Log.e(TAG, "error:${state.exception.message} ")
                    }
                }
            }

        }
    }

}