package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.uibox.R
import com.example.uibox.databinding.ItemGithubBinding
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.animateClick
import com.example.uibox.tools.applyStringSource
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

class ItemGitHubView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    data class ItemGitHubData(
        @StringSourceData val itemName: StringSource,
        @StringSourceData val itemLanguage: StringSource,
        @StringSourceData val itemOwner: StringSource,
        val itemAvatarUrl: String
    )

    private val binding = ItemGithubBinding.inflate(LayoutInflater.from(context), this@ItemGitHubView, true)

    init {
        setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_text_inverted))
        cardElevation = resources.getDimension(R.dimen.elevation_12)
        preventCornerOverlap = true
        clipToPadding = true

        shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopRightCorner(CornerFamily.ROUNDED, resources.getDimension(R.dimen.radius_25))
            .setBottomRightCorner(CornerFamily.ROUNDED, resources.getDimension(R.dimen.radius_25))
            .build()
    }

    fun configure(itemGitHubData: ItemGitHubData, clickAction: (ItemGitHubData) -> Unit) {
        with(binding) {
            itemName.applyStringSource(itemGitHubData.itemName)
            itemLanguage.applyStringSource(itemGitHubData.itemLanguage)
            Glide.with(root)
                .load(itemGitHubData.itemAvatarUrl)
                .into(avatarIcon)
        }
        setOnClickListener {
            binding.arrowNext.animateClick {
                clickAction(itemGitHubData)
            }
        }
    }
}

@BindingAdapter("bind:init", "bind:onClick")
fun ItemGitHubView.init(itemGitHubData: ItemGitHubView.ItemGitHubData?, clickAction: (ItemGitHubView.ItemGitHubData) -> Unit) {
    itemGitHubData?.let {
        configure(it, clickAction)
    }
}
