package com.example.mymarvelapp.data.model

import com.google.gson.annotations.SerializedName

data class MarvelModel(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: ListMarvel?
)

data class ListMarvel(@SerializedName("results") val result: List<ItemMarvel>)

data class ItemMarvel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail") val image: TypeImage
)

data class TypeImage(
    @SerializedName("path") val path: String,
    @SerializedName("extension") val extension: String
)
