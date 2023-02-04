package com.example.ricknmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.ricknmortyapp.R
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel<*>> : Fragment() {

    lateinit var toolbar: Toolbar

    lateinit var refresh: SwipeRefreshLayout

    var layout: Int = 0

    lateinit var binding: ViewDataBinding

    @Inject
    protected open lateinit var viewModel: VM

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract fun injectViewModel()

    protected inline fun <reified T : ViewModel> getViewModel(): T {
        return ViewModelProvider(this, viewModelFactory)[T::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            layout,
            container,
            false
        )
        return binding.root
    }

    fun bindBackNavigationButton(view: View) {
        this.toolbar = view.findViewById(R.id.toolbar)
        toolbar.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    fun bindRefreshLayout(view: View) {
        this.refresh = view.findViewById(R.id.swipe_refresh)
    }
}