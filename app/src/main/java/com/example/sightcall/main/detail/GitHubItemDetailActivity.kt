package com.example.sightcall.main.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.sightcall.databinding.ActivityDetailsBinding
import com.example.uibox.tools.animateClick
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import org.koin.androidx.viewmodel.ext.android.viewModel

class GitHubItemDetailActivity : AppCompatActivity() {
    private val gitHubItemDetailViewModel by viewModel<GitHubItemDetailViewModel>()

    private lateinit var binding: ActivityDetailsBinding

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetContainer)

        initActions()
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
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, android.R.anim.fade_out)
    }
}
