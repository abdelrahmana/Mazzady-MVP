package com.example.androidtask.util

import android.content.Context
import com.example.androidtask.ui.CurrentFragmentImplementer
import com.example.androidtask.ui.activities.RedirectActivityFirst
import com.example.androidtask.ui.activities.RedirectActivityFirst.Companion.CURRENT_IMPLEMENTER
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class GetObjectGson {
    fun getPreviewList(objectData : String): LinkedHashMap<String,String>? { // this should return the object
        val jso = objectData
        val gson = Gson()
        val typeToken = object : TypeToken<LinkedHashMap<String,String>?>() {}.type
        val obj = gson.fromJson<LinkedHashMap<String,String>?>(jso, typeToken) ?: null
        return obj

    }
    fun getCurrentImplementer(objectData : String,context: Context): CurrentFragmentImplementer? { // this should return the object
        val jso =objectData/* context.getSharedPreferences(
            "shareredPrefName", Context.MODE_PRIVATE).getString(CURRENT_IMPLEMENTER,"")*/
        val gson = Gson()
        val typeToken = object : TypeToken<CurrentFragmentImplementer?>() {}.type
        val obj = gson.fromJson<CurrentFragmentImplementer?>(jso, typeToken) ?: null
        return obj

    }
    fun setCurrentImplementer(toJson: String?,context : Context) {
        context.getSharedPreferences(
            "shareredPrefName", Context.MODE_PRIVATE).edit().putString(RedirectActivityFirst.CURRENT_IMPLEMENTER, toJson).apply()

    }
}