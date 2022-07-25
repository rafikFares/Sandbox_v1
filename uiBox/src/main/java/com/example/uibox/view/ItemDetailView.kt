package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.uibox.databinding.ViewItemDetailsBinding

class ItemDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private val binding = ViewItemDetailsBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.itemName.text = "itemName"
        binding.itemLanguage.text = "itemLanguage"
        binding.itemInformation.text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s," +
            " when an unknown printer took a galley of type and scrambled it to make a type specimen book." +
            " It has survived not only five centuries, but also the leap into electronic typesetting," +
            " remaining essentially unchanged. It was popularised in the 1960s with the release of" +
            " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software" +
            " like Aldus PageMaker including versions of Lorem Ipsum"
    }
}
