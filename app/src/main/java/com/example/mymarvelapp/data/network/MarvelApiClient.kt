package com.example.mymarvelapp.data.network

import com.example.mymarvelapp.data.model.MarvelModel
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Url

interface MarvelApiClient {
    @GET
    suspend fun getAllMarvel (@Url url:String): Response<MarvelModel>

    @GET
    suspend fun getCharacterMarvel (@Url url:String): Response<MarvelModel>
}