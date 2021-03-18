package com.example.tvseries.model

import com.google.firebase.firestore.DocumentId

class Series (
    val episodioAtual: String? = null,
    val categoria: String? = null,
    val nome: String? = null,
    @DocumentId
    val id: String? = null
) {
    override fun toString(): String = "$nome: $episodioAtual"
}