package com.example.tvseries.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvseries.database.SeriesDao
import com.example.tvseries.model.Series

class ListViewModel(
    private val seriesDao: SeriesDao
    ) : ViewModel() {

    private val _series = MutableLiveData<List<Series>>()
    val series: LiveData<List<Series>> = _series

    fun atualizarListaSeries() {

        seriesDao.all().addSnapshotListener { snapshot, error ->
                    if (error != null)
                        Log.i("ListViewModel", "${error.message}")
                    else
                        if (snapshot != null && !snapshot.isEmpty) {
                            val series =
                                    snapshot.toObjects(Series::class.java)
                            _series.value = series
                        }
        }

    }
}