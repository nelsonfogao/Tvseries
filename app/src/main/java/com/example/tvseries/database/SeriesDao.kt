package com.example.tvseries.database

import com.example.tvseries.model.Series
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface SeriesDao {

        fun createOrUpdate(series: Series): Task<Void>
        fun read(id: String): Task<DocumentSnapshot>
        fun delete(series: Series): Task<Void>
        fun all(): CollectionReference
        fun allDocuments(): Task<QuerySnapshot>
}