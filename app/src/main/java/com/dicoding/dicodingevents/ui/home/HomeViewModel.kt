package com.dicoding.dicodingevents.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevents.data.response.EventResponse
import com.dicoding.dicodingevents.data.response.ListEventsItem
import com.dicoding.dicodingevents.data.retrofit.ApiConfig
import com.dicoding.dicodingevents.ui.finished.FinishedViewModel
import com.dicoding.dicodingevents.ui.upcoming.UpcomingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val _upcomingEvent = MutableLiveData<EventResponse>()

    private val _listUpcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val listUpcomingEvent: LiveData<List<ListEventsItem>> = _listUpcomingEvent

    private val _finishedEvent = MutableLiveData<EventResponse>()

    private val _listFinishedEvent = MutableLiveData<List<ListEventsItem>>()
    val listFinishedEvent: LiveData<List<ListEventsItem>> = _listFinishedEvent

    private val _isLoadingUpcoming = MutableLiveData<Boolean>()
    val isLoadingUpcoming: LiveData<Boolean> = _isLoadingUpcoming

    private val _isLoadingFinished = MutableLiveData<Boolean>()
    val isLoadingFinished: LiveData<Boolean> = _isLoadingFinished

    private val _toastText = MutableLiveData<String>()
    val toastText: LiveData<String> = _toastText

    companion object{
    }

    init {
        getUpcomingEvent()
        getFinishedEvent()
    }

    private fun getUpcomingEvent(){
        _isLoadingUpcoming.value = true

        val client = ApiConfig.getApiService().getEvents(status = 1)

        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoadingUpcoming.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _upcomingEvent.value = responseBody!!
                    _listUpcomingEvent.value = responseBody.listEvents
                }else{
                    Log.e(UpcomingViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingUpcoming.value = false
                _toastText.value = "Failed to Load Data!"
                Log.e(UpcomingViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getFinishedEvent(){
        _isLoadingFinished.value = true

        val client = ApiConfig.getApiService().getEvents(status = 0)

        client.enqueue(object: Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoadingFinished.value = false
                val responseBody = response.body()
                if(response.isSuccessful){
                    _finishedEvent.value = responseBody!!
                    _listFinishedEvent.value = responseBody.listEvents
                }else{
                    Log.e(FinishedViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoadingFinished.value = false
                _toastText.value = "Failed to Load Data!"
                Log.e(FinishedViewModel.TAG, "onFailure: ${t.message}")
            }
        })
    }
}