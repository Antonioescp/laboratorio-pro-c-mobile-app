package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class SelectDateDialog(
    private val listener: DatePickerDialog.OnDateSetListener
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = with(Calendar.getInstance()) {
        val dialog = DatePickerDialog(
            requireContext(),
            listener,
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DAY_OF_MONTH)
        )

        dialog.datePicker.minDate = System.currentTimeMillis()
        return dialog
    }
}