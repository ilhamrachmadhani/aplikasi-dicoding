package com.dicoding.dicodingevents.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevents.ui.database.FavoriteDao
import com.dicoding.dicodingevents.ui.database.FavoriteEvent
import com.dicoding.dicodingevents.ui.database.FavoriteRoomDatabase
import com.dicoding.dicodingevents.ui.repository.FavoriteRepository

class MainViewModel(application: Application,  favoriteRepository: FavoriteRepository) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = favoriteRepository

    suspend fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> = mFavoriteRepository.getAllFavoriteEvents()
}