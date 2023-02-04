package com.example.ricknmortyapp.ui.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.location.Location
import com.example.ricknmortyapp.BR
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.di.Injector
import com.example.ricknmortyapp.ui.BaseFragment
import com.example.ricknmortyapp.ui.adapter.PaginationScrollListener
import com.example.ricknmortyapp.ui.adapter.RecyclerBindingAdapter

class LocationListFragment : BaseFragment<LocationListViewModel>() {

    lateinit var recyclerView: RecyclerView

    private var adapter: RecyclerBindingAdapter<Location> =
        RecyclerBindingAdapter(R.layout.item_location, BR.location_item)

    var onRefreshEnd: () -> Unit = { }
    var onLoadingData: () -> Unit = { }
    var onLoadingDataEnd: () -> Unit = { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.locationListComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getNext()
        return inflater.inflate(R.layout.fragment_entity_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list_recycler)
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner, { item ->
            if (item.data?.size == 0) {
                Toast.makeText(this.context, "No data", Toast.LENGTH_SHORT).show()
            }
            adapter.updateAdapter(item.data)
            onRefreshEnd()
            onLoadingDataEnd()
        })

        onLoadingData = {
            if (viewModel.isLoading) view.findViewById<ProgressBar>(R.id.progress).visibility =
                View.VISIBLE
        }

        onLoadingDataEnd = {
            view.findViewById<ProgressBar>(R.id.progress).visibility = View.INVISIBLE
        }

        adapter.onClick = { item, _ ->
            apply {
                val fragment = LocationFragment(item.id)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment, fragment)
                    addToBackStack(LocationListFragment::class.java.canonicalName)
                    commit()
                }
            }
        }

        recyclerView.addOnScrollListener(object :
            PaginationScrollListener(recyclerView.layoutManager as GridLayoutManager) {

            override fun isLastPage(): Boolean {
                return viewModel.isLastPage()
            }

            override fun isLoading(): Boolean {
                return viewModel.isLoading
            }

            override fun loadMoreItems() {
                loadNextPage()
                onLoadingData()
            }
        })
    }

    fun fetch() {
        viewModel.fetch()
    }

    fun filter(name: String = "", type: String = "", dimension: String = "") {
        viewModel.filter(name, type, dimension)
    }

    private fun loadNextPage() {
        viewModel.getNext()
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

}