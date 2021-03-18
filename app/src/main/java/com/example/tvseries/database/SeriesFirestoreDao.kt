package com.example.tvseries.database

import com.example.tvseries.model.Series
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

class SeriesFirestoreDao: SeriesDao {

    private val collection = FirebaseFirestore
            .getInstance()
            .collection("series")



    override fun createOrUpdate(series: Series): Task<Void> {
        return collection
            .document(series.id!!)
            .set(series)
    }

    override fun delete(series: Series): Task<Void> {
        return collection
                .document(series.id!!)
                .delete()
    }

    override fun read(id: String): Task<DocumentSnapshot> {
        return collection
                .document(id)
                .get()
    }

    override fun all(): CollectionReference {
        return collection
    }

    override fun allDocuments(): Task<QuerySnapshot> {
        return collection.get()
    }

}