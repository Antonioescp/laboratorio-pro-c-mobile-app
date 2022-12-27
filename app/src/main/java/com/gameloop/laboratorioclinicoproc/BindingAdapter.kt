package com.gameloop.laboratorioclinicoproc

import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.gameloop.laboratorioclinicoproc.database.model.patient.Sex

@BindingAdapter("age")
fun TextView.setAge(age: Int?) {
    age?.let {
        val current = text.toString().toIntOrNull()
        if (current != null) {
            if (age != current) {
                text = age.coerceAtLeast(1).toString()
            }
        }
    }
}

@InverseBindingAdapter(attribute = "age", event = "android:textAttrChanged")
fun TextView.getAge(): Int = text.toString().toIntOrNull() ?: 0

@BindingAdapter("sex")
fun RadioGroup.setSex(sex: Sex?) {
    sex?.let {
        val currentSex = when (checkedRadioButtonId) {
            R.id.rbFemale -> Sex.FEMALE
            R.id.rbMale -> Sex.MALE
            else -> null
        }

        if (sex != currentSex) {
            check(when(sex) {
                Sex.MALE -> R.id.rbMale
                Sex.FEMALE -> R.id.rbFemale
            })
        }
    }
}

@InverseBindingAdapter(attribute = "sex", event = "android:checkedButtonAttrChanged")
fun RadioGroup.getSex(): Sex? {
    return when (checkedRadioButtonId) {
        R.id.rbMale -> Sex.MALE
        R.id.rbFemale -> Sex.FEMALE
        else -> null
    }
}