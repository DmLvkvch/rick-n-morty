package com.example.ricknmortyapp.ui.location

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.ricknmortyapp.R

class LocationFilterDialogFragment : DialogFragment() {

    private var nameEdit: EditText? = null

    private var dimensionEdit: EditText? = null

    private var typeEdit: EditText? = null

    private var name: String? = null

    private var dimension: String? = null

    private var type: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        name = requireArguments().getString(NAME_EXTRA)
        dimension = requireArguments().getString(DIMENSION_EXTRA)
        type = requireArguments().getString(TYPE_EXTRA)
    }

    override fun onResume() {
        super.onResume()

        nameEdit = dialog?.findViewById(R.id.location_filter_name)
        nameEdit?.setText(name)

        dimensionEdit = dialog?.findViewById(R.id.location_filter_dimension)
        dimensionEdit?.setText(dimension)

        typeEdit = dialog?.findViewById(R.id.location_filter_type)
        typeEdit?.setText(type)

    }

    companion object {
        const val NAME_EXTRA = "NAME_EXTRA"
        const val DIMENSION_EXTRA = "GENDER_EXTRA"
        const val TYPE_EXTRA = "TYPE_EXTRA"

        fun newInstance(name: String? = "", type: String? = "", dimension: String? = "") =
            LocationFilterDialogFragment().apply {
                arguments = Bundle().also {
                    it.putString(NAME_EXTRA, name)
                    it.putString(DIMENSION_EXTRA, type)
                    it.putString(TYPE_EXTRA, dimension)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.location_filter_dialog_fragment, null))
                .setPositiveButton(
                    "Сохранить"
                ) { dialog, id ->

                    val intent = Intent()
                    intent.putExtra(NAME_EXTRA, nameEdit?.text.toString())
                    intent.putExtra(DIMENSION_EXTRA, dimensionEdit?.text.toString())
                    intent.putExtra(TYPE_EXTRA, typeEdit?.text.toString())
                    targetFragment?.onActivityResult(
                        targetRequestCode,
                        Activity.RESULT_OK,
                        intent
                    )
                    dialog.dismiss()

                }
                .setNegativeButton("Отмена",
                    { dialog, id ->
                        getDialog()?.cancel()
                    })

            builder.create()
        } ?: throw IllegalStateException()
    }
}