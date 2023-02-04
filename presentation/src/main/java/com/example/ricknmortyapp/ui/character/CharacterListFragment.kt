package com.example.ricknmortyapp.ui.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entities.character.Character
import com.example.ricknmortyapp.BR
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.di.Injector
import com.example.ricknmortyapp.ui.BaseFragment
import com.example.ricknmortyapp.ui.adapter.PaginationScrollListener
import com.example.ricknmortyapp.ui.adapter.RecyclerBindingAdapter

class CharacterListFragment(private val ids: String? = null) :
    BaseFragment<CharacterListViewModel>() {

    lateinit var recyclerView: RecyclerView

    private var adapter: RecyclerBindingAdapter<Character> =
        RecyclerBindingAdapter(R.layout.item_character, BR.character_item)

    var onRefreshEnd: () -> Unit = { }
    var onLoadingData: () -> Unit = { }
    var onLoadingDataEnd: () -> Unit = { }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.characterListComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter.onClick = { item, _ ->
            apply {
                val characterFragment = CharacterFragment.newInstance(item.id)
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.nav_host_fragment, characterFragment, item.id.toString())
                    addToBackStack(CharacterListFragment::class.java.canonicalName)
                    commit()
                }
            }
        }
        when (ids) {
            null -> {
                viewModel.getCharacters()
            }
            else -> {
                viewModel.getCharacters(ids)
            }
        }
        return inflater.inflate(R.layout.fragment_entity_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.list_recycler)
        recyclerView.adapter = adapter

        viewModel.items.observe(viewLifecycleOwner) { item ->
            if (item.data?.size == 0) {
                Toast.makeText(this.context, "No data", Toast.LENGTH_SHORT).show()
            }
            adapter.updateAdapter(item.data)
            onRefreshEnd()
        }

        recyclerView.addOnScrollListener(
            object :
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

    fun filter(
        name: String = "",
        status: String = "",
        species: String = "",
        type: String = "",
        gender: String = ""
    ) {
        viewModel.filter(name, status, species, type, gender)
    }

    fun fetch() {
        viewModel.fetch()
    }

    private fun loadNextPage() {
        viewModel.getCharacters()
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }
}