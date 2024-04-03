package com.timesnews.app.repository

import com.timesnews.app.model.MostViewedNews
import com.timesnews.app.network.ApiManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MostViewedNewsRepo @Inject constructor(private val apiManager: ApiManager) {


    private val _mostViewedNews =
        MutableStateFlow<MostViewedUiState>(MostViewedUiState.Loading(""))
    val mostViewedNews: StateFlow<MostViewedUiState> = _mostViewedNews

    suspend fun getMostViewedNews(section: String) {
        val data = apiManager.getMostViewedNews(section)
        if (data.isSuccessful && data.body() != null) {
            _mostViewedNews.value = MostViewedUiState.Success(data.body()!!.results)
        } else {
            _mostViewedNews.value =
                MostViewedUiState.Error(Exception(data.errorBody().toString()))
        }

    }


}

sealed class MostViewedUiState {
    data class Loading(val loading: String) : MostViewedUiState()
    data class Success(val news: List<MostViewedNews.Result>) : MostViewedUiState()
    data class Error(val exception: Throwable) : MostViewedUiState()
}
