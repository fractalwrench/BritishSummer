package com.fractalwrench.britishsummer

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

internal fun Context.showKeyboard(view: View) {
    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, 0)
}
