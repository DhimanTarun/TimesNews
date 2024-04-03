package com.timesnews.app.viewModels

import androidx.lifecycle.ViewModel
import com.timesnews.app.repository.MostViewedNewsRepo
import com.timesnews.app.repository.MostViewedUiState
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostViewedViewModel @Inject constructor(private val mostViewedNewsRepo: MostViewedNewsRepo) : ViewModel() {

    // The UI collects from this StateFlow to get its state updates
    val uiState: StateFlow<MostViewedUiState>
        get() = mostViewedNewsRepo.mostViewedNews


    fun getMostViewedNews(section: String) {
        viewModelScope.launch {
            mostViewedNewsRepo.getMostViewedNews(section)
        }
    }


}