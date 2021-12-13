package com.example.mymarvelapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymarvelapp.data.model.MarvelModel
import com.example.mymarvelapp.domain.GetMarvelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMarvelViewModel @Inject constructor(private var getMarvels: GetMarvelUseCase) :
    ViewModel() {
    var mutableGetMarvels = MutableLiveData<MarvelModel?>()

    fun onCreate() {
        viewModelScope.launch {
            mutableGetMarvels.postValue(getMarvels())
        }
    }
}