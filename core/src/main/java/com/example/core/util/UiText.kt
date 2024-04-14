package com.example.core.util

import android.content.Context

sealed class UiText {

    data class DynamicString(val text: String) : UiText()
    data class StringRes(val resId: Int) : UiText()

    fun asString(context: Context): String {

        return when (this) {
            is DynamicString -> text
            is StringRes -> context.getString(resId)
        }
    }
}