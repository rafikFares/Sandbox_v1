package com.example.sandbox.main.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityGithubItemsBinding
import com.example.sandbox.main.detail.ItemDetailActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val homeViewModel by viewModel<HomeViewModel>()

    private lateinit var binding: ActivityGithubItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_items)
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this@HomeActivity
        lifecycle.addObserver(homeViewModel)

        initActions()
    }

    private fun initActions() {
        binding.searchView.setExitAction {
            //finish()
            startActivity(Intent(this@HomeActivity, ItemDetailActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.uiState
                    .collect { state ->
                        when (state) {
                            is HomeViewModel.UiState.ItemClick -> {
                                val intent = ItemDetailActivity.create(
                                    context = this@HomeActivity,
                                    ownerName = state.item.repositoryOwner,
                                    repoName = state.item.repositoryName,
                                )
                                startActivity(intent)
                            }
                            else -> {

                            }
                        }
                    }
            }
        }

    }
}
