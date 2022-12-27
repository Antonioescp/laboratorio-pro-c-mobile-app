package com.gameloop.laboratorioclinicoproc

import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
        } else {
            text = age.coerceAtLeast(1).toString()
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

@BindingAdapter("patient_sex")
fun ImageView.setPatientSex(sex: Sex?) {
    sex?.let {
        setImageResource(when(sex){
            Sex.MALE -> R.drawable.ic_baseline_male_24_tint
            Sex.FEMALE -> R.drawable.ic_baseline_female_24
        })
    }
}

@BindingAdapter("patient_sex_at_end")
fun TextView.setPatientTextAtEnd(sex: Sex?) {
    sex?.let {
        val drawable = ContextCompat.getDrawable(context, when (sex) {
            Sex.MALE -> R.drawable.ic_baseline_male_24_tint
            Sex.FEMALE -> R.drawable.ic_baseline_female_24
        })

        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
    }
}