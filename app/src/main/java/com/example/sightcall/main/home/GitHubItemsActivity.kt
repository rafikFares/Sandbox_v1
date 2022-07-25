package com.example.sightcall.main.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.sightcall.R
import com.example.sightcall.databinding.ActivityGithubItemsBinding
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
            finish()
        }
    }
}
