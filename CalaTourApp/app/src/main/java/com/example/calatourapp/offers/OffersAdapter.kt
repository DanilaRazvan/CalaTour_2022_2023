package com.example.calatourapp.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.calatourapp.databinding.FragmentOffersListItemBinding
import com.example.calatourapp.models.Offer

class OffersAdapter : RecyclerView.Adapter<OffersAdapter.OffersViewHolder>() {

    private val dataSource = mutableListOf<Offer>()

    fun setData(offers: List<Offer>) {
        dataSource.clear()
        dataSource.addAll(offers)
        notifyDataSetChanged()
    }

    inner class OffersViewHolder(
        val binding: FragmentOffersListItemBinding
    ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffersViewHolder {
        val binding = FragmentOffersListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OffersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OffersViewHolder, position: Int) {
        val offer = dataSource[position]

        with(holder) {
            binding.title.text = "${offer.name}, ${offer.period}"
            binding.description.text = offer.description
            binding.image.load(
                data = offer.imageUrl
            )
        }
    }

    override fun getItemCount(): Int = dataSource.size
}