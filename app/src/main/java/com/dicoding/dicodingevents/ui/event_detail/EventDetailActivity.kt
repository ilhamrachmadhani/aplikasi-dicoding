package com.dicoding.dicodingevents.ui.event_detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.dicodingevents.R
import com.dicoding.dicodingevents.data.response.DetailEventItem
import com.dicoding.dicodingevents.databinding.ActivityEventDetailBinding

class EventDetailActivity : AppCompatActivity() {
    private lateinit var detailEventActivityDetailEventBinding: ActivityEventDetailBinding
    private val detailEventViewModel: EventDetailViewModel by viewModels<EventDetailViewModel>()
    private val favoriteKey = "FAVORITE_EVENT"


    companion object{
        const val IDEVENT = "id_event"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        detailEventActivityDetailEventBinding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(detailEventActivityDetailEventBinding.root)

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val idEvent = intent.getStringExtra(IDEVENT)
        println("DEBUG ID EVENT: $idEvent")
        detailEventViewModel.getDetailEvent(idEvent!!)
        detailEventViewModel.detailEvent.observe(this){ dataEvent ->
            setDetailEventData(dataEvent.event)
        }

        detailEventViewModel.isLoading.observe(this){
            showLoading(it)
        }

        detailEventViewModel.toastText.observe(this){ toastText ->
            Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupFavoriteButton()
    }

    private fun setupFavoriteButton() {
        val fabFavorite = detailEventActivityDetailEventBinding.fabFavorite

        // Cek status favorit dari SharedPreferences
        val isFavorite = getFavoriteStatus()
        updateFavoriteButton(isFavorite)

        // Tambahkan listener untuk klik tombol favorit
        fabFavorite.setOnClickListener {
            val newStatus = !getFavoriteStatus() // Toggle status favorit
            saveFavoriteStatus(newStatus)
            updateFavoriteButton(newStatus)
        }
    }

    private fun saveFavoriteStatus(isFavorite: Boolean) {
        val sharedPref = getSharedPreferences("FavoritePrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(favoriteKey, isFavorite)
            apply()
        }
    }

    private fun getFavoriteStatus(): Boolean {
        val sharedPref = getSharedPreferences("FavoritePrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean(favoriteKey, false)
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val fabFavorite = detailEventActivityDetailEventBinding.fabFavorite
        if (isFavorite) {
            fabFavorite.setImageResource(R.drawable.baseline_favorite_24) // Ubah ikon menjadi "favorite filled"
        } else {
            fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24) // Ubah ikon menjadi "favorite border"
        }
    }

    private fun setDetailEventData(dataEvent: DetailEventItem){
        supportActionBar?.title = dataEvent.name
        detailEventActivityDetailEventBinding.nameDetailEvent.text = dataEvent.name
        detailEventActivityDetailEventBinding.ownerNameDetailEvent.text = dataEvent.ownerName
        detailEventActivityDetailEventBinding.beginTimeDetailEvent.text = "Waktu Mulai: " + dataEvent.beginTime
        detailEventActivityDetailEventBinding.quotaDetailEvent.text = "Kuota: " + dataEvent.quota.toString()
        detailEventActivityDetailEventBinding.registrantDetailEvent.text = "Sisa Kuota: " + (dataEvent.quota - dataEvent.registrants).toString()
        detailEventActivityDetailEventBinding.descriptionDetailEvent.text = HtmlCompat.fromHtml(
            dataEvent.description,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        Glide.with(this).load(dataEvent.mediaCover).into(detailEventActivityDetailEventBinding.imgDetailEvent)

        detailEventActivityDetailEventBinding.register.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(dataEvent.link)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean){
        detailEventActivityDetailEventBinding.register.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
        detailEventActivityDetailEventBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}