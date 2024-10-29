package com.dicoding.dicodingevents.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.dicodingevents.ui.database.FavoriteDao
import com.dicoding.dicodingevents.ui.repository.FavoriteRepository
import com.dicoding.dicodingevents.ui.setting.SettingPreferences

class ViewModelFactory(
    private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}