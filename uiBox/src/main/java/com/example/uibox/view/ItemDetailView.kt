package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.uibox.R
import com.example.uibox.databinding.ViewItemDetailsBinding
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.applyStringSource

class ItemDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    data class ItemDetailData(
        @StringSourceData val itemName: StringSource,
        @StringSourceData val itemLanguage: StringSource,
        val itemAvatarUrl: String,
        val otherInformation: List<StringSource>
    )

    private val binding = ViewItemDetailsBinding.inflate(LayoutInflater.from(context), this)

    fun configure(itemDetailData: ItemDetailData): ItemDetailsView {
        with(binding) {
            itemName.applyStringSource(itemDetailData.itemName)
            itemLanguage.applyStringSource(itemDetailData.itemLanguage)
            Glide.with(root)
                .load(itemDetailData.itemAvatarUrl)
                .into(itemIcon)
            itemDetailData.otherInformation.forEach {
                val childView = createTextView(it)
                itemInformationContainer.addView(childView)
            }
        }
        return this
    }

    private fun createTextView(text: StringSource): AppCompatTextView {
        return AppCompatTextView(context).apply {
            applyStringSource(text)
            setTextColor(ContextCompat.getColor(context, R.color.color_text_secondary))
            setTextAppearance(context, R.style.Body)
        }
    }
}

@BindingAdapter("bind:initDetails")
fun ItemDetailsView.initDetails(itemDetailData: ItemDetailsView.ItemDetailData?) {
    itemDetailData?.let {
        configure(it)
    }
}
