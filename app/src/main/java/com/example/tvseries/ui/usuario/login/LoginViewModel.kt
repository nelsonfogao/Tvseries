package com.example.tvseries.ui.usuario.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvseries.database.UsuarioFirebaseDao

class LoginViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun verificarCredenciais(email: String, senha: String) {
        UsuarioFirebaseDao
            .verificarCredenciais(email, senha) // signInWithEmailAndPassword
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                _msg.value = it.message
            }
    }
}