package com.example.sandbox.main.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.sandbox.BuildConfig
import com.example.sandbox.R
import com.example.sandbox.core.repository.remote.RemoteRepository
import com.example.sandbox.databinding.ActivityDetailsBinding
import com.example.sandbox.main.platform.BaseAppcompatActivity
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.animateClick
import com.example.uibox.view.ItemDetailsView
import com.example.uibox.view.initDetails
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailActivity : BaseAppcompatActivity() {
    private val itemDetailViewModel by viewModel<ItemDetailViewModel>()

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetContainer)

        initActions()

        initData()
    }

    private fun initData() {
        val ownerName = intent.getStringExtra(OwnerNameKey).orEmpty()
        val repoName = intent.getStringExtra(RepoNameKey).orEmpty()
        check(ownerName.isNotEmpty()) {
            "ownerName is empty"
        }
        check(repoName.isNotEmpty()) {
            "repoName is empty"
        }
        itemDetailViewModel.loadData(
            RemoteRepository.RepositoryInformation(
                ownerName = ownerName, repoName = repoName
            )
        )
    }

    private fun initActions() {
        binding.containerLayout.setOnClickListener {
            finish()
        }
        binding.closeButton.setOnClickListener {
            it.animateClick {
                finish()
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.topSlider.isVisible = false
                        binding.closeButton.isVisible = true
                        binding.informationView.transitionToEnd()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.topSlider.isVisible = true
                        binding.closeButton.isVisible = false
                        binding.informationView.transitionToStart()
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        finish()
                    }
                    else -> {
                        // nothing
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // nothing
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                itemDetailViewModel.uiState
                    .collect { state ->
                        when (state) {
                            is ItemDetailViewModel.UiState.Error -> {
                                manageError(state.exception) { finish() }
                            }
                            ItemDetailViewModel.UiState.Loading -> {
                                binding.informationView.isVisible = false
                                binding.detailLoading.isVisible = true
                            }
                            is ItemDetailViewModel.UiState.UpdateData -> {
                                val tmpDetails = ItemDetailsView.ItemDetailData(
                                    itemName = StringSource.String(state.gitHubItemDetails.repositoryName),
                                    itemLanguage = StringSource.String(state.gitHubItemDetails.repositoryLanguage),
                                    itemAvatarUrl = state.gitHubItemDetails.repositoryOwnerAvatarUrl,
                                    otherInformation = listOf(
                                        StringSource.String(state.gitHubItemDetails.repositoryOwner),
                                        StringSource.String(state.gitHubItemDetails.repositorySshUrl),
                                        StringSource.String(state.gitHubItemDetails.repositoryDefaultBranch),
                                        StringSource.String(state.gitHubItemDetails.repositoryLicence.orEmpty())
                                    ),
                                    lottieEndAnimation = R.raw.halloween
                                )
                                binding.informationView.initDetails(tmpDetails)

                                binding.detailLoading.isVisible = false
                                binding.informationView.isVisible = true
                            }
                            else -> {
                                // nothing
                            }
                        }
                    }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, android.R.anim.fade_out)
    }

    private fun log(message: String, exception: Exception? = null) {
        if (BuildConfig.DEBUG) {
            Log.d("GitHubItemDetailActivity", message, exception)
        }
    }

    companion object {
        private const val OwnerNameKey = "owner_name_key"
        private const val RepoNameKey = "repo_name_key"

        fun create(context: Context, ownerName: String, repoName: String): Intent {
            return Intent(context, ItemDetailActivity::class.java).apply {
                putExtra(OwnerNameKey, ownerName)
                putExtra(RepoNameKey, repoName)
            }
        }
    }
}
