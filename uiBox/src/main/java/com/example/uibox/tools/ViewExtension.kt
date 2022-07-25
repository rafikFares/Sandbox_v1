package com.example.uibox.tools

import android.view.View

fun View.animateClick(endTask: (() -> Unit)? = null) {
    apply {
        animate()
            .setDuration(100L)
            .scaleX(1.3f)
            .scaleY(1.3f)
            .withEndAction {
                this.scaleX = 1f
                this.scaleY = 1f
                endTask?.invoke()
            }
            .start()
    }
}
