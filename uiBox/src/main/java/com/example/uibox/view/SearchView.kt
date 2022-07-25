package com.example.uibox.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.example.uibox.databinding.ViewSearchBinding
import com.example.uibox.tools.StringSource
import com.example.uibox.tools.StringSourceData
import com.example.uibox.tools.animateClick
import com.example.uibox.tools.toString

class SearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ViewSearchBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.searchTextContainer.setEndIconOnClickListener {
            binding.searchTextField.setText("")
        }
    }

    fun setText(@StringSourceData newText: StringSource): SearchView {
        binding.searchTextField.setText(newText.toString(context))
        return this
    }

    fun setSearchAction(action: (String) -> Unit): SearchView {
        binding.searchButton.setOnClickListener {
            it.animateClick {
                val searchedText = binding.searchTextField.text
                action.invoke("$searchedText")
            }
        }
        return this
    }

    fun setExitAction(action: () -> Unit): SearchView {
        binding.exitButton.setOnClickListener {
            it.animateClick {
                action.invoke()
            }
        }
        return this
    }
}

@BindingAdapter("bind:initText")
fun SearchView.initText(text: StringSource?) {
    text?.let {
        setText(text)
    }
}

@BindingAdapter("bind:initSearchAction")
fun SearchView.initSearchAction(action: (String) -> Unit) {
    setSearchAction(action)
}

@BindingAdapter("bind:initExitAction")
fun SearchView.initExitAction(action: () -> Unit) {
    setExitAction(action)
}
