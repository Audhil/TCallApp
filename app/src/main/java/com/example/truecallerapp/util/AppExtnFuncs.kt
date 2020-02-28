package com.example.truecallerapp.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.truecallerapp.AppApplication

inline fun Any.showVLog(log: () -> String) =
    Log.v("---" + this::class.java.simpleName, log())

inline fun Any.showELog(log: () -> String) =
    Log.e("---" + this::class.java.simpleName, log())

inline fun Any.showDLog(log: () -> String) =
    Log.d("---" + this::class.java.simpleName, log())

inline fun Any.showILog(log: () -> String) =
    Log.i("---" + this::class.java.simpleName, log())

inline fun Any.showWLog(log: () -> String) =
    Log.w("---" + this::class.java.simpleName, log())


var toast: Toast? = null

fun Any.showToast(context: Context? = AppApplication.INSTANCE, duration: Int = Toast.LENGTH_SHORT) {
    toast?.cancel()
    toast = when (this) {
        is String ->
            Toast.makeText(context, this, duration)
        is Int ->
            Toast.makeText(context, this, duration)
        else ->
            Toast.makeText(context, "Invalid input to Toast! :-(", duration)
    }
    toast?.show()
}