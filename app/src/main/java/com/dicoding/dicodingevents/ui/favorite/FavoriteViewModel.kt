package com.dicoding.dicodingevents.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevents.ui.database.FavoriteDao
import com.dicoding.dicodingevents.ui.database.FavoriteEvent
import com.dicoding.dicodingevents.ui.repository.FavoriteRepository
import com.dicoding.dicodingevents.ui.setting.SettingPreferences
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository) : ViewModel() {


    private val _eventStatus = MutableLiveData<EventStatus>()
    val eventStatus: LiveData<EventStatus> get() = _eventStatus

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> {
        return repository.getFavoriteEventById(id)
    }

    fun getFavoriteEvents(): LiveData<List<FavoriteEvent>> {
        return repository.getAllFavoriteEvents()
    }

    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            try {
                repository.insertFavoriteEvent(favoriteEvent)
                _eventStatus.value = EventStatus.Success("Event berhasil ditambahkan")
            } catch (e: Exception) {
                _eventStatus.value = EventStatus.Error("Gagal menambahkan event: ${e.message}")
            }
        }
    }

    fun deleteFavoriteEvent(favoriteEvent: FavoriteEvent) {
        viewModelScope.launch {
            try {
                repository.deleteFavoriteEvent(favoriteEvent)
                _eventStatus.value = EventStatus.Success("Event berhasil dihapus")
            } catch (e: Exception) {
                _eventStatus.value = EventStatus.Error("Gagal menghapus event: ${e.message}")
            }
        }
    }
    sealed class EventStatus {
        data class Success(val message: String) : EventStatus()
        data class Error(val message: String) : EventStatus()
    }
}
