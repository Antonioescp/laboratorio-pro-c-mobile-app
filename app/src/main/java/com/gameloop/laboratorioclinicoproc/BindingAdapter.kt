package com.gameloop.laboratorioclinicoproc

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import com.bumptech.glide.Glide
import com.gameloop.laboratorioclinicoproc.database.model.appointment.Appointment
import com.gameloop.laboratorioclinicoproc.database.model.patient.Sex
import java.text.DateFormat
import java.util.*

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

@BindingAdapter("img_url")
fun ImageView.setImgByUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

@BindingAdapter("price")
fun TextView.setPrice(price: Double?) {
    price?.let {
        text = String.format("C\$%.2f", price)
    }
}

// Appointment binding adapters
@BindingAdapter("appointmentStatus")
fun TextView.setAppointmentStatus(status: Appointment.State?) {
    status?.let {
        var color: Int? = null
        var message: String = ""
        when (status) {
            Appointment.State.Pending -> {
                color = ContextCompat.getColor(context, R.color.pending_appointment)
                message = "Pendiente"
            }
            Appointment.State.Cancelled -> {
                color = ContextCompat.getColor(context, R.color.cancelled_appointment)
                message = "Cancelado"
            }
            Appointment.State.InProgress -> {
                color = ContextCompat.getColor(context, R.color.processing_appointment)
                message = "En progreso"
            }
            Appointment.State.Completed -> {
                color = ContextCompat.getColor(context, R.color.completed_appointment)
                message = "Completado"
            }
        }

        // Updating message and textView drawable color
        text = message
        compoundDrawables.forEach { drawable ->
            drawable?.let {
                drawable.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }
    }
}

@BindingAdapter("date")
fun TextView.setDate(date: Date?) {
    date?.let {
        val formattedDate = DateFormat
            .getDateInstance(DateFormat.SHORT, Locale.forLanguageTag("es"))
            .format(date)

        val dateString = "Fecha: $formattedDate"
        text = dateString
    }
}