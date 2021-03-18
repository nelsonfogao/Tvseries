package com.example.tvseries.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId

class Usuario (
    var nome: String? = null,
    var firebaseUser: FirebaseUser? = null,
    @DocumentId
    var uid: String? = null
)