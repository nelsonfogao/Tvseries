package com.example.tvseries.ui.usuario.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tvseries.database.UsuarioFirebaseDao

class CadastroViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun salvarCadastro(email: String, senha: String,
                       nome: String) {
        UsuarioFirebaseDao
            .cadastrarCredenciais(email, senha) // createUserWithEmailAndPassword
            .addOnSuccessListener {
                val uid = it.user!!.uid         // firebaseUser.uid
                UsuarioFirebaseDao
                    .cadastrarPerfil(uid, nome)   // collection("usuarios").document(uid)
                    .addOnSuccessListener {
                        _status.value = true
                        _msg.value = "Usuário cadastrado com sucesso!"
                    }
                    .addOnFailureListener {
                        _msg.value = "${it.message}"
                    }
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }
    }
}