package com.example.ricknmortyapp.ui.episode

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.ricknmortyapp.R

class EpisodeFilterDialogFragment : DialogFragment() {

    private var nameEdit: EditText? = null

    private var episodeEdit: EditText? = null

    private var name: String? = null

    private var episode: String? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)

        name = requireArguments().getString(NAME_EXTRA)
        episode = requireArguments().getString(EPISODE_EXTRA)
    }

    override fun onResume() {
        super.onResume()

        nameEdit = dialog?.findViewById(R.id.episode_filter_name)
        nameEdit?.setText(name)

        episodeEdit = dialog?.findViewById(R.id.episode_filter_episode)
        episodeEdit?.setText(episode)
    }

    companion object {
        const val NAME_EXTRA = "NAME_EXTRA"
        const val EPISODE_EXTRA = "EPISODE_EXTRA"

        fun newInstance(name: String? = "", episode: String? = "") =
            EpisodeFilterDialogFragment().apply {
                arguments = Bundle().also {
                    it.putString(NAME_EXTRA, name)
                    it.putString(EPISODE_EXTRA, episode)
                }
            }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.episode_filter_dialog_fragment, null))
                .setPositiveButton(
                    "Сохранить"
                ) { dialog, id ->

                    val intent = Intent()
                    intent.putExtra(NAME_EXTRA, nameEdit?.text.toString())
                    intent.putExtra(EPISODE_EXTRA, episodeEdit?.text.toString())
                    targetFragment?.onActivityResult(
                        targetRequestCode,
                        Activity.RESULT_OK,
                        intent
                    )
                    dialog.dismiss()

                }
                .setNegativeButton("Отмена"
                ) { _, _ ->
                    dialog?.cancel()
                }

            builder.create()
        } ?: throw IllegalStateException()
    }
}