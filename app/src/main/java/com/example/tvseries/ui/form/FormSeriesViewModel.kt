package com.example.tvseries.ui.form

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tvseries.database.SeriesDao
import com.example.tvseries.model.Series
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class FormSeriesViewModel (
        private val seriesDao: SeriesDao,
        application: Application
) : AndroidViewModel(application) {

    private val app = application

    private val _imagemSeries = MutableLiveData<Uri>()
    val imagemSeries: LiveData<Uri> = _imagemSeries

    private val _series = MutableLiveData<Series>()
    val series: LiveData<Series> = _series

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String?>()
    val msg: LiveData<String?> = _msg

    init {
        _status.value = false
        _msg.value = null
    }

    fun salvarSeries(
            episodioatual: String, categoria: String, nome: String, id: String) {
        _status.value = false
        _msg.value = "Por favor, aguarde a persistência!"

        val series = Series(episodioatual, categoria, nome,id)

        uploadImageSeries(id)
        seriesDao.createOrUpdate(series)
                .addOnSuccessListener {
                    _status.value = true
                    _msg.value = "Persistência realizada!"
                }
                .addOnFailureListener {
                    _msg.value = "Persistência falhou!"
                    Log.e("SeriesDaoFirebase", "${it.message}")
                }
    }

    fun deletarSeries(series: Series) {
        _status.value = false
        _msg.value = "Por favor, aguarde a deleção!"

        getFileReference(series.id!!).delete()
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Deleção realizada!"
            }
            .addOnFailureListener {
                _msg.value = "deleção falhou!"
                Log.e("SeriesDaoFirebase", "${it.message}")
            }
        seriesDao.delete(series)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Deleção realizada!"
            }
            .addOnFailureListener {
                _msg.value = "deleção falhou!"
                Log.e("SeriesDaoFirebase", "${it.message}")
            }
    }


    // Storage
    fun uploadImageSeries(id: String) {
        getFileReference(id)
                .putFile(imagemSeries!!.value!!)
                .addOnSuccessListener {
                    _msg.value = "Imagem persistida com sucesso."
                }
                .addOnFailureListener {
                    _msg.value = "Imagem não pode ser carregada: ${it.message}"
                }
    }

    fun setUpImagemSeries(id: String) {
        val file = File(app.cacheDir, "temp.png")
        getFileReference(id)
            .getFile(file)
                .addOnSuccessListener {
                    setImagemSeries(file.toUri())
                }
                .addOnFailureListener {
                    _msg.value = "Imagem não pode ser carregada: ${it.message}"
                }
    }

    private fun getFileReference(id: String): StorageReference {
        val firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.reference
        val fileReference = storageReference.child("Series/$id.png")
        return fileReference
    }

    fun setImagemSeries(photo: Uri) {
        _imagemSeries.value = photo
    }

    fun selectSerie(id: String) {
        seriesDao.read(id)
            .addOnSuccessListener {
                val series = it.toObject(Series::class.java)
                if (series != null)
                    _series.value = series!!
                else
                    _msg.value = "A serie foi encontrada."
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }

    }
}