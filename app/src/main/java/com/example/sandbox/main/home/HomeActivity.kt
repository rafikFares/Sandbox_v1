package com.example.sandbox.main.home

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sandbox.R
import com.example.sandbox.databinding.ActivityGithubItemsBinding
import com.example.sandbox.main.alert.DefaultAlert
import com.example.sandbox.main.detail.ItemDetailActivity
import com.example.sandbox.main.platform.BaseAppcompatActivity
import com.example.uibox.tools.StringSource
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseAppcompatActivity() {

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
            lifecycleScope.launch {
                val result = DefaultAlert.create(this@HomeActivity, message = StringSource.Res(R.string.message_exit))
                when (result) {
                    DefaultAlert.AlertResult.Positive -> {
                        finish()
                    }
                    DefaultAlert.AlertResult.Negative,
                    DefaultAlert.AlertResult.Cancel -> {
                        // Nothing
                    }
                }
            }
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
                            is HomeViewModel.UiState.Error -> {
                                binding.itemListLoading.isVisible = false
                                manageError(state.exception)
                            }
                            HomeViewModel.UiState.Loading -> {
                                binding.itemsListContainer.isVisible = false
                                binding.itemListLoading.isVisible = true
                            }
                            HomeViewModel.UiState.Complete -> {
                                binding.itemListLoading.isVisible = false
                                binding.itemsListContainer.isVisible = true
                            }
                            else -> {
                                // nothing
                            }
                        }
                    }
            }
        }

    }
}
