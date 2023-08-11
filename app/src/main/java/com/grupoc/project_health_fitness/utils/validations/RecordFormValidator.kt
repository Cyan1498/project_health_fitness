package com.grupoc.project_health_fitness.utils.validations

import com.grupoc.project_health_fitness.databinding.FragmentRecordFormBinding


class RecordFormValidator(private val binding: FragmentRecordFormBinding) {

    fun validateForm(): Boolean {
        return validateName() && validateDateInit() && validateInstruction() && validateUnit() && validateNumDias() && validateNote()
    }

    fun validateName(): Boolean {
        val name = binding.edName.text.toString().trim()
        return if (name.isEmpty()) {
            binding.edNameL.error = "Required"
            false
        }else {
            binding.edNameL.error = null
            true
        }
    }

    fun validateDateInit(): Boolean {
        val dateInit = binding.dtPicker.text.toString().trim()
        return if (dateInit.isEmpty()) {
            binding.dtPickerL.error = "Required"
            false
        }else {
            binding.dtPickerL.error = null
            true
        }
    }

    fun validateInstruction(): Boolean {
        val selectedInst = binding.autoCompInstruction.text.toString().trim()
        return if (selectedInst.isEmpty()) {
            binding.autoCompInstructionL.error = "Required"
            false
        } else {
            binding.autoCompInstructionL.error = null
            true
        }
    }

    fun validateUnit(): Boolean {
        val selectedUnit = binding.autoCompUnits.text.toString().trim()
        return if (selectedUnit.isEmpty()) {
            binding.autoCompUnitsL.error = "Required"
            false
        } else {
            binding.autoCompUnitsL.error = null
            true
        }
    }

    fun validateNumDias(): Boolean {
        val name = binding.edNumdias.text.toString().trim()
        return if (name.isEmpty()) {
            binding.edNumdiasL.error = "Required"
            false
        }else {
            binding.edNumdiasL.error = null
            true
        }
    }

    fun validateNote(): Boolean {
        val name = binding.edNote.text.toString().trim()
        return if (name.isEmpty()) {
            binding.edNoteL.error = "Required"
            false
        }else {
            binding.edNoteL.error = null
            true
        }
    }
}