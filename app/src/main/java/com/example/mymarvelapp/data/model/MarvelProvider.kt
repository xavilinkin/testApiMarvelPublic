package com.example.mymarvelapp.data.model

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarvelProvider @Inject constructor() {
    var allMarvels: MarvelModel? = null
    var characterMarvel: MarvelModel? = null
}