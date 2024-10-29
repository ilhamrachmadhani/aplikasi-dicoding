package com.dicoding.dicodingevents.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.dicodingevents.R
import com.dicoding.dicodingevents.ui.database.FavoriteEvent

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoritesList = listOf<FavoriteEvent>()

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val eventImageView: ImageView = itemView.findViewById(R.id.ivEventImage)
        private val eventNameTextView: TextView = itemView.findViewById(R.id.tvEventName)
        private val eventDescriptionTextView: TextView = itemView.findViewById(R.id.tvEventDescription)

        fun bind(favoriteEvent: FavoriteEvent) {
            eventNameTextView.text = favoriteEvent.name
            eventDescriptionTextView.text = "Description of the event"

            // Muat gambar menggunakan Glide atau Coil
            Glide.with(itemView.context)
                .load(favoriteEvent.mediaCover)
                .into(eventImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoritesList[position])
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    fun submitList(favorites: List<FavoriteEvent>) {
        favoritesList = favorites
        notifyDataSetChanged()
    }
}