package com.example.tvseries.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvseries.database.SeriesDao

class ListSeriesViewModelFactory (
        private val seriesDao: SeriesDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java))
            return ListViewModel(seriesDao) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }
}