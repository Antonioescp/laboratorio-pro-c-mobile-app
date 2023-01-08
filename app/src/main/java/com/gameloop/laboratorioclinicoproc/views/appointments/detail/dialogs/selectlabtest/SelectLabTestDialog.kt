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
    private val alreadySelectedLabTest: List<LabTest>,
    private val availableLabTestCategories: LiveData<List<LabTestCategory>>,
    private val listener: Listener
) : DialogFragment() {

    interface Listener {
        fun onConfirm(labTests: List<LabTest>) {}
        fun onCancel(labTests: List<LabTest>) {}
    }

    private lateinit var binding: DialogSelectLabTestBinding
    private val selectedLabTests = mutableListOf<LabTest>()

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
        binding.btnConfirm.setOnClickListener {
            dismiss()
            listener.onConfirm(selectedLabTests)
        }
    }

    private fun setUpCancelButton() {
        binding.btnCancel.setOnClickListener {
            dismiss()
            listener.onCancel(selectedLabTests)
        }
    }

    private fun setUpCategoryList() {
        val adapter = DialogLabCategoryAdapter(
            alreadySelectedLabTest,
            object: DialogLabTestAdapter.Listener {
                override fun onSelectChange(labTest: LabTest, isSelected: Boolean) {
                    if (isSelected && selectedLabTests.contains(labTest).not()) {
                        selectedLabTests.add(labTest)
                    } else if (isSelected.not() && selectedLabTests.contains(labTest)) {
                        selectedLabTests.remove(labTest)
                    }
                }
            }
        )

        binding.rvCategories.adapter = adapter

        availableLabTestCategories.observe(this) {
            it?.let {
                adapter.submitList(it)
            }
        }
    }
}