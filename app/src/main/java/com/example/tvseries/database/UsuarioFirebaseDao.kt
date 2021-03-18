package com.example.tvseries.database

import com.example.tvseries.model.Usuario
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

object UsuarioFirebaseDao {

    val firebaseAuth = FirebaseAuth.getInstance()
    private val collection = FirebaseFirestore
        .getInstance()
        .collection("usuarios")


    fun cadastrarCredenciais(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth
            .createUserWithEmailAndPassword(email, senha)
    }

    fun cadastrarPerfil(uid: String, nome: String): Task<Void> {
        return collection
            .document(uid)
            .set(Usuario(nome))
    }


    fun verificarCredenciais(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth
            .signInWithEmailAndPassword(email, senha)
    }

    fun encerrarSessao() {
        firebaseAuth.signOut()
    }

}