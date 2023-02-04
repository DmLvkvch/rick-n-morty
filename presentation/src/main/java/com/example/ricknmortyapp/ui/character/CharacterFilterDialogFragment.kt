package com.example.ricknmortyapp.ui.character

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.ricknmortyapp.R

class CharacterFilterDialogFragment : DialogFragment() {

    private var nameEdit: EditText? = null

    private var genderEdit: EditText? = null

    private var statusEdit: EditText? = null

    private var speciesEdit: EditText? = null

    private var typeEdit: EditText? = null

    private var name: String? = null

    private var gender: String? = null

    private var species: String? = null

    private var status: String? = null

    private var type: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        name = requireArguments().getString(NAME_EXTRA)
        gender = requireArguments().getString(GENDER_EXTRA)
        status = requireArguments().getString(STATUS_EXTRA)
        species = requireArguments().getString(SPECIES_EXTRA)
        type = requireArguments().getString(TYPE_EXTRA)
    }

    override fun onResume() {
        super.onResume()

        nameEdit = dialog?.findViewById(R.id.character_filter_name)
        nameEdit?.setText(name)

        genderEdit = dialog?.findViewById(R.id.character_filter_gender)
        genderEdit?.setText(gender)

        statusEdit = dialog?.findViewById(R.id.character_filter_status)
        statusEdit?.setText(status)

        speciesEdit = dialog?.findViewById(R.id.character_filter_species)
        speciesEdit?.setText(species)

        typeEdit = dialog?.findViewById(R.id.character_filter_type)
        typeEdit?.setText(type)

    }

    companion object {
        const val NAME_EXTRA = "NAME_EXTRA"
        const val GENDER_EXTRA = "GENDER_EXTRA"
        const val STATUS_EXTRA = "STATUS_EXTRA"
        const val SPECIES_EXTRA = "SPECIES_EXTRA"
        const val TYPE_EXTRA = "TYPE_EXTRA"

        fun newInstance(
            name: String? = "",
            gender: String? = "",
            status: String? = "",
            species: String? = "",
            type: String? = ""
        ) =
            CharacterFilterDialogFragment().apply {
                arguments = Bundle().also {
                    it.putString(NAME_EXTRA, name)
                    it.putString(GENDER_EXTRA, gender)
                    it.putString(STATUS_EXTRA, status)
                    it.putString(SPECIES_EXTRA, species)
                    it.putString(TYPE_EXTRA, type)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.character_filter_dialog_fragment, null))
                .setPositiveButton(
                    "Сохранить"
                ) { dialog, id ->

                    val intent = Intent()
                    intent.putExtra(NAME_EXTRA, nameEdit?.text.toString())
                    intent.putExtra(GENDER_EXTRA, genderEdit?.text.toString())
                    intent.putExtra(STATUS_EXTRA, statusEdit?.text.toString())
                    intent.putExtra(TYPE_EXTRA, typeEdit?.text.toString())
                    intent.putExtra(SPECIES_EXTRA, speciesEdit?.text.toString())
                    targetFragment?.onActivityResult(
                        targetRequestCode,
                        Activity.RESULT_OK,
                        intent
                    )
                    dialog.dismiss()

                }
                .setNegativeButton("Отмена"
                ) { dialog, id ->
                    getDialog()?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException()
    }
}