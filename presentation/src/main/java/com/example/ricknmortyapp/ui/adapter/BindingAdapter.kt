package com.example.ricknmortyapp.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.findFragment
import com.bumptech.glide.Glide
import com.example.domain.entities.character.CharacterGender
import com.example.domain.entities.character.CharacterLocation
import com.example.domain.entities.character.CharacterOrigin
import com.example.domain.entities.character.CharacterStatus
import com.example.ricknmortyapp.R
import com.example.ricknmortyapp.ui.character.CharacterFragment
import com.example.ricknmortyapp.ui.location.LocationFragment

@BindingAdapter(value = ["app:url"])
fun urlToImageView(view: ImageView, value: String?) {
    Glide.with(view.context)
        .load(value)
        .into(view)
}

@BindingAdapter(value = ["android:text"])
fun genderToTextView(view: TextView, value: CharacterGender?) {
    view.text = value?.name ?: CharacterGender.UNKNOWN.name
}

@BindingAdapter(value = ["android:text"])
fun statusToTextView(view: TextView, value: CharacterStatus?) {
    view.text = value?.name ?: CharacterStatus.UNKNOWN.name
}

@BindingAdapter(value = ["android:text"])
fun originToTextView(view: TextView, value: CharacterOrigin?) {
    view.text = value?.name ?: ""
}

@BindingAdapter(value = ["android:text"])
fun locationToTextView(view: TextView, value: CharacterLocation?) {
    view.text = value?.name ?: ""
}

@BindingAdapter(value = ["app:location"])
fun onLocationClicked(view: TextView, location: CharacterLocation?) {
    val parent = view.findFragment<CharacterFragment>()
    view.setOnClickListener {
        val id: Int = location?.url?.split("/")?.last()?.toInt() ?: -1
        bindViewLocation(parent, id)
    }
}

@BindingAdapter(value = ["app:origin"])
fun onOriginClicked(view: TextView, location: CharacterOrigin?) {
    val parent = view.findFragment<CharacterFragment>()
    view.setOnClickListener {
        val id: Int = location?.url?.split("/")?.last()?.toInt() ?: -1
        bindViewLocation(parent, id)
    }
}

fun bindViewLocation(parent: Fragment, id: Int) {
    val fragment = LocationFragment(id)
    parent.parentFragmentManager.beginTransaction().apply {
        replace(R.id.nav_host_fragment, fragment)
        addToBackStack(CharacterFragment::class.java.canonicalName)
        commit()
    }
}