package com.example.sightcall.main.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sightcall.R
import com.example.sightcall.databinding.ActivityGithubItemsBinding
import com.example.sightcall.main.detail.GitHubItemDetailActivity
import com.example.sightcall.main.detail.GitHubItemsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubItemsActivity : AppCompatActivity() {

    private val gitHubItemsViewModel by viewModel<GitHubItemsViewModel>()

    private lateinit var binding: ActivityGithubItemsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_github_items)
        binding.viewmodel = gitHubItemsViewModel
        binding.lifecycleOwner = this@GitHubItemsActivity
        lifecycle.addObserver(gitHubItemsViewModel)

        initActions()
    }

    private fun initActions() {
        binding.searchView.setExitAction {
            //finish()
            startActivity(Intent(this@GitHubItemsActivity, GitHubItemDetailActivity::class.java))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gitHubItemsViewModel.uiState
                    .collect { state ->
                        when (state) {
                            is GitHubItemsViewModel.UiState.ItemClick -> {
                                val intent = GitHubItemDetailActivity.create(
                                    context = this@GitHubItemsActivity,
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
