package com.example.androidtask.util

import android.app.Dialog
import android.view.View

fun View.setVisibilty(visibleOrHidden : Int) {
    visibility = visibleOrHidden
}
fun Dialog.showHide(hide: Boolean) {
    if (hide)
        dismiss()
    else
        show()

}