package com.example.ricknmortyapp.ui.character

import android.os.Bundle
import android.view.View
import com.example.ricknmortyapp.BR
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.di.Injector
import com.example.ricknmortyapp.ui.BaseFragment
import com.example.ricknmortyapp.ui.episode.EpisodeListFragment


class CharacterFragment : BaseFragment<CharacterViewModel>() {

    private var characterId = -1

    companion object {
        fun newInstance(id: Int) = CharacterFragment().apply {
            characterId = id
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.characterComponent.inject(this)
        layout = R.layout.fragment_character
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.id = characterId
        viewModel.fetch()
        viewModel.character.observe(viewLifecycleOwner) { item ->
            binding.setVariable(BR.character, item.data)
            refresh.isRefreshing = false
            item.data?.episode?.let { getEpisodesIds(it) }
        }

        bindBackNavigationButton(view)
        bindRefreshLayout(view)
        refresh.isRefreshing = true
        refresh.setOnRefreshListener {
            viewModel.fetch()
        }
    }

    private fun getEpisodesIds(episodes: MutableList<String>) {
        val tmp = StringBuilder()
        episodes.let {
            episodes.forEach { r ->
                tmp.append(r.split("/").last())
                tmp.append(",")
            }
        }
        val episodeListFragment = EpisodeListFragment(tmp.toString())
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.character_details_episode_list, episodeListFragment)
            commit()
        }
    }

    override fun injectViewModel() {
        viewModel = getViewModel()
    }

}