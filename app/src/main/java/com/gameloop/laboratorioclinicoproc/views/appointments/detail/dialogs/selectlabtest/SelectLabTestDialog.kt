package com.gameloop.laboratorioclinicoproc.views.appointments.detail.dialogs.selectlabtest

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LiveData
import com.gameloop.laboratorioclinicoproc.database.model.labtest.LabTest
import com.gameloop.laboratorioclinicoproc.database.model.labtestcategory.LabTestCategory
import com.gameloop.laboratorioclinicoproc.databinding.DialogSelectLabTestBinding

class SelectLabTestDialog(
    private val availableLabTestCategories: LiveData<List<LabTestCategory>>
) : DialogFragment() {

    private lateinit var binding: DialogSelectLabTestBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        binding = DialogSelectLabTestBinding.inflate(inflater)

        setUpCategoryList()
        setUpCancelButton()
        setUpConfirmButton()

        return builder.setView(binding.root).create()
    }

    private fun setUpConfirmButton() {
        binding.btnConfirm.setOnClickListener { dismiss() }
    }

    private fun setUpCancelButton() {
        binding.btnCancel.setOnClickListener { dismiss() }
    }

    private fun setUpCategoryList() {
        val adapter = DialogLabCategoryAdapter(object: DialogLabTestAdapter.Listener{
            override fun onSelectChange(labTest: LabTest, isSelected: Boolean) {
                val message = if (isSelected) "was selected" else "was unselected"
                Toast.makeText(
                    requireContext(),
                    "${labTest.title} $message",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.rvCategories.adapter = adapter

        availableLabTestCategories.observe(this) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}