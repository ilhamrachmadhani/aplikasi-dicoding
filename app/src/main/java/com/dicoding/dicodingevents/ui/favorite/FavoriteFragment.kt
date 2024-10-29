package com.dicoding.dicodingevents.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.dicodingevents.R
import com.dicoding.dicodingevents.databinding.FragmentFavoriteBinding
import com.dicoding.dicodingevents.ui.database.FavoriteEvent
import com.dicoding.dicodingevents.ui.database.FavoriteRoomDatabase
import com.dicoding.dicodingevents.ui.repository.FavoriteRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // Initialize favoriteRepository and favoriteViewModel
        val favoriteDao = FavoriteRoomDatabase.getDatabase(requireContext()).favoriteDao()
        val favoriteRepository = FavoriteRepository(favoriteDao)
        val factory = FavoriteViewModelFactory(favoriteRepository)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

        // Initialize RecyclerView
        favoriteAdapter = FavoriteAdapter()
        binding.fabFavorite.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab: FloatingActionButton = view.findViewById(R.id.fabFavorite)

        val eventId = "eventId"
        favoriteViewModel.getFavoriteEventById(eventId).observe(viewLifecycleOwner) { favoriteEvent ->
            if (favoriteEvent == null) {
                fab.setImageResource(R.drawable.baseline_favorite_border_24)
            } else {
                fab.setImageResource(R.drawable.baseline_favorite_24)
            }
        }

        fab.setOnClickListener {
            val favoriteEvent = FavoriteEvent(id = eventId, name = "Event Name", mediaCover = "url")
            favoriteViewModel.getFavoriteEventById(eventId).observe(viewLifecycleOwner) { existingEvent ->
                if (existingEvent == null) {
                    favoriteViewModel.insertFavoriteEvent(favoriteEvent)
                } else {
                    favoriteViewModel.deleteFavoriteEvent(existingEvent)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

