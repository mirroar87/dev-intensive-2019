package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    if (isKeyboardClosed()) return
    val imm: InputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
}

fun Activity.isKeyboardOpen():Boolean {
    val rootView: View = this.window.decorView
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    val metrics = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(metrics)
    return metrics.heightPixels - (rect.height()) > 128
}

fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}