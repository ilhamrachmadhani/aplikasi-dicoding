package com.dicoding.dicodingevents.data.retrofit

import com.dicoding.dicodingevents.data.response.DetailEventResponse
import com.dicoding.dicodingevents.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events")
    fun getEvents(
        @Query("active") status: Int // 0 for finished events, 1 for upcoming events
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailEventResponse>
}