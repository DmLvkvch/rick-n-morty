package com.example.ricknmortyapp.ui.episode

import android.os.Bundle
import android.view.View
import com.example.ricknmortyapp.BR
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.di.Injector
import com.example.ricknmortyapp.ui.BaseFragment
import com.example.ricknmortyapp.ui.character.CharacterListFragment

class EpisodeFragment(private val episodeId: Int = -1) : BaseFragment<EpisodeViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.episodeComponent.inject(this)
        layout = R.layout.fragment_episode
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.id = episodeId
        viewModel.fetch()
        viewModel.episode.observe(viewLifecycleOwner, { item ->
            binding.setVariable(BR.episode, item.data)
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
            val characters = viewModel.episode.value?.data?.characters
            val tmp = StringBuilder()
            characters?.let {
                characters.forEach { r ->
                    tmp.append(r.split("/").last())
                    tmp.append(",")
                }
            }
            val characterListFragment = CharacterListFragment(tmp.toString())
            replace(R.id.episode_details_character_list, characterListFragment)
            commit()
        }
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }
}