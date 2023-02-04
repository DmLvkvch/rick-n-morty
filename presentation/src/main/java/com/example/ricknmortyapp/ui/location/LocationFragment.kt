package com.example.ricknmortyapp.ui.location

import android.os.Bundle
import android.view.View
import com.example.ricknmortyapp.BR
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.di.Injector
import com.example.ricknmortyapp.ui.BaseFragment
import com.example.ricknmortyapp.ui.character.CharacterListFragment

class LocationFragment(private val locationId: Int = -1) : BaseFragment<LocationViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.locationComponent.inject(this)
        layout = R.layout.fragment_location
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.id = locationId
        viewModel.fetch()
        viewModel.location.observe(viewLifecycleOwner, { item ->
            binding.setVariable(BR.location, item.data)
            refresh.isRefreshing = false
            characterListFragment()
        })

        bindBackNavigationButton(view)
        bindRefreshLayout(view)
        refresh.isRefreshing = true
        refresh.setOnRefreshListener {
            viewModel.fetch()
        }
    }

    private fun characterListFragment() {
        parentFragmentManager.beginTransaction().apply {
            val residents = viewModel.location.value?.data?.residents
            val tmp = StringBuilder()
            residents?.let {
                residents.forEach { r ->
                    tmp.append(r.split("/").last())
                    tmp.append(",")
                }
            }
            val characterListFragment = CharacterListFragment(tmp.toString())
            replace(R.id.location_details_character_list, characterListFragment)
            commit()
        }
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }
}