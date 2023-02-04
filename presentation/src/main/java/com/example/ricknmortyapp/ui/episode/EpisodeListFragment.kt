package com.example.ricknmortyapp.ui.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.episode.Episode
import com.example.ricknmortyapp.BR
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.di.Injector
import com.example.ricknmortyapp.ui.BaseFragment
import com.example.ricknmortyapp.ui.adapter.PaginationScrollListener
import com.example.ricknmortyapp.ui.adapter.RecyclerBindingAdapter

class EpisodeListFragment(
    private val ids: String? = null
) :
    BaseFragment<EpisodeListViewModel>() {

    lateinit var recyclerView: RecyclerView

    private var adapter: RecyclerBindingAdapter<Episode> =
        RecyclerBindingAdapter(R.layout.item_episode, BR.episode_item)

    var onRefreshEnd: () -> Unit = { }
    var onLoadingData: () -> Unit = { }
    var onLoadingDataEnd: () -> Unit = { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.episodeListComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        when (ids) {
            null -> {
                viewModel.getNext()
            }
            else -> {
                viewModel.getData(ids)
            }
        }

        adapter.onClick = { item, _ ->
            apply {
                val fragment = EpisodeFragment(item.id)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment, fragment)
                    addToBackStack(EpisodeListFragment::class.java.canonicalName)
                    commit()
                }
            }
        }

        return inflater.inflate(R.layout.fragment_entity_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list_recycler)
        if (ids != null) {
            recyclerView.layoutManager = GridLayoutManager(this.context, 1)
        }
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

    fun filter(name: String = "", episode: String = "") {
        viewModel.filter(name, episode)
    }

    private fun loadNextPage() {
        viewModel.getNext()
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

}