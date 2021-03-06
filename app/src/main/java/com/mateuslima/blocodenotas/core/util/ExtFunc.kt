package com.mateuslima.blocodenotas.core.util

import android.content.res.Resources
import androidx.appcompat.widget.SearchView

inline fun SearchView.setOnQueryTextChange(crossinline search: (String) -> Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            search.invoke(newText)
            return true
        }

    })
}

inline fun SearchView.setOnQueryTexSubmit(crossinline search: (String) -> Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String): Boolean {
            search.invoke(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            return false
        }

    })
}

val Int.dp : Int get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()