package com.example.ricknmortyapp.ui.character

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.di.Injector

class CharacterNavigationFragment : Fragment() {

    lateinit var refresh: SwipeRefreshLayout

    var name = ""
    var gender = ""
    var status = ""
    var type = ""
    var species = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.characterListComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.enitity_list_screen_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refresh = view.findViewById(R.id.swipe_refresh)
        refresh.setOnRefreshListener {
            val childFragment = parentFragmentManager.findFragmentByTag(getString(R.string.character_list_fragment_tag))
            if (childFragment is CharacterListFragment) {
                childFragment.fetch()
            }
        }
        val fragment = CharacterListFragment()
        fragment.onRefreshEnd = {
            refresh.isRefreshing = false
        }
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.replaced_layout, fragment, getString(R.string.character_list_fragment_tag))
            commit()
        }

        view.findViewById<ImageButton>(R.id.filter_button).setOnClickListener {
            val dialogFragment =
                CharacterFilterDialogFragment.newInstance(name, gender, status, species, type)
            dialogFragment.setTargetFragment(this, 1)
            dialogFragment.show(parentFragmentManager, getString(R.string.dialog_fragment_tag))
        }

        val search = view.findViewById<EditText>(R.id.search)
        var work: Runnable
        val handler = Handler(Looper.getMainLooper())
        search.doAfterTextChanged {
            handler.removeCallbacksAndMessages(null)
            work = Runnable { filter(search.text.toString()) }
            handler.postDelayed(work, 300)
        }
    }

    fun filter(text: String) {
        val childFragment =
            parentFragmentManager.findFragmentByTag(getString(R.string.character_list_fragment_tag))
        if (childFragment is CharacterListFragment) {
            childFragment.filter(text)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    name =
                        data?.getStringExtra(CharacterFilterDialogFragment.NAME_EXTRA).toString()
                    gender =
                        data?.getStringExtra(CharacterFilterDialogFragment.GENDER_EXTRA).toString()
                    status =
                        data?.getStringExtra(CharacterFilterDialogFragment.STATUS_EXTRA).toString()
                    type =
                        data?.getStringExtra(CharacterFilterDialogFragment.TYPE_EXTRA).toString()
                    species =
                        data?.getStringExtra(CharacterFilterDialogFragment.SPECIES_EXTRA).toString()

                    val childFragment =
                        parentFragmentManager.findFragmentByTag(getString(R.string.character_list_fragment_tag))
                    if (childFragment is CharacterListFragment) {
                        childFragment.filter(name, status, species, type, gender)
                    }
                }
            }
        }
    }
}