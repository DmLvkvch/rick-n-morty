package com.example.ricknmortyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class RecyclerBindingAdapter<T : Any>(private val layout: Int, private val variableId: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items = mutableListOf<T>()
    var onClick: (item: T, position: Int) -> Unit = { _, _ -> }

    fun updateAdapter(updatedList: MutableList<T>?) {
        if (updatedList == null) {
            return
        }
        val result = DiffUtil.calculateDiff(DiffCallback(items, updatedList), true)
        this.items.clear()
        this.items.addAll(updatedList)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BindingHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            onClick(item, position)
        }
        holder as BindingHolder
        holder.binding?.setVariable(variableId, item)
    }

    override fun getItemCount() = items.size


    class BindingHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding: ViewDataBinding? = DataBindingUtil.bind(v)
    }

    class DiffCallback<T>(
        private val oldList: List<T>,
        private val newList: List<T>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}