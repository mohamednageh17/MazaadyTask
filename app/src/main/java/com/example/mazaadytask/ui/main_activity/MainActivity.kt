package com.example.mazaadytask.ui.main_activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.domain.remote.response.category.Categories
import com.example.domain.remote.response.category.Children
import com.example.mazaadytask.R
import com.example.mazaadytask.base.MainViewState
import com.example.mazaadytask.databinding.ActivityMainBinding
import com.example.mazaadytask.ui.main_activity.adapters.PropertiesAdapter
import com.example.mazaadytask.utilis.setAsSpinner
import com.example.mazaadytask.utilis.showTableDialog
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
        binding.submitBtn.setOnClickListener{
            showTableDialog(adapter.collectSelectedValues())
            adapter.collectSelectedValues().forEach {
                Log.d("nageh",  it.first+"->"+it.second)
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

                    }

                    is MainViewState.Success -> {
                        state.categories?.let { categories ->
                            initCategories(categories)
                        }
                        state.properties?.let { properties ->
                            adapter.setData(properties)
                        }
                        state.optionsChild?.let { optionsChild ->
                            // Handle options child data
                            Log.d("nageh", "collect children:$optionsChild ")
                            Log.d("nageh", "selectedProperty:${ viewModel.selectedProperty} ")
                            viewModel.selectedProperty?.let {
                                Log.d("nageh", "update adapter: ")
                                adapter.updateChildOptions(it,optionsChild)
                            }
                        }

                    }

                    is MainViewState.Error -> {
                        Log.e(TAG, "error:${state.exception.message} ")
                    }
                }
            }

        }
    }

}