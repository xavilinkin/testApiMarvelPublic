package com.example.mymarvelapp.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymarvelapp.data.model.ItemMarvel
import com.example.mymarvelapp.databinding.ItemMarvelBinding
import com.example.mymarvelapp.ui.view.fragments.listener.OnClickListMarvelListener

class ListMarvelAdapter(
    private val listMarvel: List<ItemMarvel>,
    private val listener: OnClickListMarvelListener
) :
    RecyclerView.Adapter<ListMarvelAdapter.ListMarvelHolder>() {

    private lateinit var binding: ItemMarvelBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMarvelHolder {
        binding = ItemMarvelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListMarvelHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListMarvelHolder, position: Int) {
        val item = listMarvel[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listMarvel.size

    inner class ListMarvelHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(itemMarvel: ItemMarvel) {
            binding.nameMarvelTextView.text = itemMarvel.name
            binding.nameMarvelCardView.setOnClickListener {
                listener.goToCharacter(itemMarvel.id.toString())
            }
        }
    }
}
