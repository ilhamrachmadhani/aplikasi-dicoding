package com.dicoding.dicodingevents.ui.repository

import androidx.lifecycle.LiveData
import com.dicoding.dicodingevents.ui.database.FavoriteDao
import com.dicoding.dicodingevents.ui.database.FavoriteEvent

class FavoriteRepository(private val favoriteDao: FavoriteDao) {

    suspend fun insertFavoriteEvent(event: FavoriteEvent) {
        favoriteDao.insertFavoriteEvent(event) // Langsung gunakan favoriteDao
    }

    fun getAllFavoriteEvents(): LiveData<List<FavoriteEvent>> {
        return favoriteDao.getAllFavoriteEvents() // Tidak perlu suspend, cukup panggil metode DAO
    }

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> {
        return favoriteDao.getFavoriteEventById(id) // Tambahkan fungsi ini jika belum ada
    }

    suspend fun deleteFavoriteEvent(event: FavoriteEvent) {
        favoriteDao.deleteFavoriteEvent(event) // Langsung gunakan favoriteDao
    }
}
