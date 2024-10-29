package com.dicoding.dicodingevents.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevents.data.response.EventResponse
import com.dicoding.dicodingevents.data.response.ListEventsItem
import com.dicoding.dicodingevents.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel: ViewModel() {
    private val _event = MutableLiveData<EventResponse?>() // Nullable EventResponse

    private val _listEvent = MutableLiveData<List<ListEventsItem>>() // ListEvents tidak nullable
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    companion object {
        const val TAG = "EventModel"
    }

    init {
        getUpcomingEvent()
    }

    private fun getUpcomingEvent() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getEvents(status = 1)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _event.value = responseBody
                    _listEvent.value = responseBody.listEvents // Handle jika listEvents null
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    _toastText.value = "Gagal memuat event mendatang."
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = "Gagal Memuat Data!"
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
