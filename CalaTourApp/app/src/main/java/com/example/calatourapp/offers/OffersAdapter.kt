package com.example.calatourapp.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.calatourapp.R
import com.example.calatourapp.databinding.FragmentOffersListItemBinding
import com.example.calatourapp.models.Offer
import java.util.UUID

class OffersAdapter(
    private val offersClickListener: OffersClickListener
) : RecyclerView.Adapter<OffersAdapter.OffersViewHolder>() {

    interface OffersClickListener {
        fun onAdd(index: Int)
        fun onRemove(id: UUID)
        fun itemSelected(id: UUID)
    }

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

            binding.root.setOnLongClickListener {
                val popupMenu = PopupMenu(
                    binding.root.context,
                    binding.root
                )
                popupMenu.inflate(R.menu.offers_context_menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when(menuItem.itemId) {
                        R.id.add_offer -> {
                            offersClickListener.onAdd(position + 1)
                            true
                        }

                        R.id.remove_offer -> {
                            offersClickListener.onRemove(offer.id)
                            true
                        }

                        else -> false
                    }
                }

                popupMenu.show()
                false
            }
        }
    }

    override fun getItemCount(): Int = dataSource.size
}