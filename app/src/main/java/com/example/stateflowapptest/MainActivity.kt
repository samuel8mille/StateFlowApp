package com.example.stateflowapptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.stateflowapptest.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGetSomething.setOnClickListener {
            viewModel.getSomething()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.uiStateFlow.collect { uiState ->
                when(uiState) {
                    MainViewModel.UiState.Success -> {
                        binding.run {
                            flipperFlow.displayedChild = 1
                            progressFlow.isVisible = false
                            binding.textHelloFlow.text = getString(R.string.hello_flow)
                        }
                    }
                    MainViewModel.UiState.Error -> {
                        binding.run {
                            flipperFlow.displayedChild = 1
                            progressFlow.isVisible = false
                            binding.textHelloFlow.text = getString(R.string.error)
                        }
                    }
                    MainViewModel.UiState.Loading -> {
                        binding.run {
                            flipperFlow.displayedChild = 0
                            progressFlow.isVisible = true
                        }
                    }
                    MainViewModel.UiState.Initial -> binding.progressFlow.isVisible = false
                }
            }
        }


    }
}