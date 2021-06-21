package com.example.geekbrainsmoviesapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.geekbrainsmoviesapp.databinding.FragmentItemBinding
import com.example.geekbrainsmoviesapp.model.Contact

class MyContactRecyclerViewAdapter(var placeholderPhone: String) :
    RecyclerView.Adapter<MyContactRecyclerViewAdapter.ViewHolder>() {
    private val list = mutableListOf<Contact>()

    fun setData(toAdd: List<Contact>) {
        val callback = ContactDiffUtilCallback(list, toAdd)
        DiffUtil.calculateDiff(callback).run {
            list.clear()
            list.addAll(toAdd)
            dispatchUpdatesTo(this@MyContactRecyclerViewAdapter)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.phone.text = item.phone ?: placeholderPhone
    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = binding.name
        val phone: TextView = binding.phone
    }

    inner class ContactDiffUtilCallback(
        private val list: List<Contact>,
        private val toAdd: List<Contact>
    ) :
        DiffUtil.Callback() {
        override fun getOldListSize() = list.size

        override fun getNewListSize() = toAdd.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            list[oldItemPosition].id == toAdd[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            list[oldItemPosition] == toAdd[newItemPosition]

    }

}