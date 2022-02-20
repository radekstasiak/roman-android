package io.radev.roman.ui.places

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.radev.roman.CoroutineDispatcher
import io.radev.roman.data.PlacesRepository
import kotlinx.coroutines.launch

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

class PlacesViewModel(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val placesRepository: PlacesRepository
) : ViewModel() {

    fun getPlaces() {
        viewModelScope.launch(coroutineDispatcher.IO) {
            placesRepository.getPlaces("restaurants", "53.801277", "-1.548567")
        }
    }
}