package com.gameloop.laboratorioclinicoproc

import android.widget.TextView

fun TextView.validateEmpty(errorMsg: String): Boolean {
    if (text.isNullOrEmpty()) {
        error = errorMsg
        return false
    }
    return true
}

fun TextView.validateLength(min: Int, max: Int, errorMsg: String): Boolean {
    if (text.length < min || text.length > max)  {
        error = errorMsg
        return false
    }
    return true
}

fun TextView.validateRegex(pattern: Regex, errorMsg: String): Boolean {
    val content = text.toString()
    return if (!pattern.containsMatchIn(content))  {
        error = errorMsg
        false
    }
    else true
}

fun TextView.validatePredicate(errorMsg: String, predicate: (view: TextView) -> Boolean): Boolean {
    return if (!predicate(this)) {
        error = errorMsg
        false
    }
    else true
}