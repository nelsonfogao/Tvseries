package com.example.tvseries.ui.form

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tvseries.database.SeriesDao

class FormSeriesViewModelFactory (
    val seriesDao: SeriesDao,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormSeriesViewModel::class.java)){
            return FormSeriesViewModel(seriesDao, application) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }

}