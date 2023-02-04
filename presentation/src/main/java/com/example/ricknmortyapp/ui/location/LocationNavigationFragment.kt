package com.example.ricknmortyapp.ui.location

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

class LocationNavigationFragment : Fragment() {

    private lateinit var refresh: SwipeRefreshLayout
    private var name = ""
    private var type = ""
    private var dimension = ""

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
            val childFragment =
                parentFragmentManager.findFragmentByTag(getString(R.string.location_list_tag))
            if (childFragment is LocationListFragment) {
                childFragment.fetch()
            }
        }

        val fragment = LocationListFragment()
        fragment.onRefreshEnd = {
            refresh.isRefreshing = false
        }
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.replaced_layout, fragment, getString(R.string.location_list_tag))
            commit()
        }

        view.findViewById<ImageButton>(R.id.filter_button).setOnClickListener {
            val dialogFragment = LocationFilterDialogFragment.newInstance(name, type, dimension)
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
        try {
            val childFragment =
                parentFragmentManager.findFragmentByTag(getString(R.string.location_list_tag))
            if (childFragment is LocationListFragment) {
                childFragment.filter(text)
            }
        } catch (e: Exception) {
            print(e.message)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                1 -> {
                    name =
                        data?.getStringExtra(LocationFilterDialogFragment.NAME_EXTRA).toString()
                    dimension =
                        data?.getStringExtra(LocationFilterDialogFragment.DIMENSION_EXTRA)
                            .toString()
                    type =
                        data?.getStringExtra(LocationFilterDialogFragment.TYPE_EXTRA).toString()

                    val childFragment =
                        parentFragmentManager.findFragmentByTag(getString(R.string.location_list_tag))
                    if (childFragment is LocationListFragment) {
                        childFragment.filter(name, type, dimension)
                    }
                }
            }
        }
    }
}