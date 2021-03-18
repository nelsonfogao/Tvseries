package com.example.tvseries

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tvseries.model.Series
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.recyclerview.view.*

class RecyclerListSeries (
        private val series: List<Series>,
        val actionClick: (Series) -> Unit
) : RecyclerView.Adapter<RecyclerListSeries.SeriesViewHolder>() {

    private val storageReference = FirebaseStorage.getInstance().reference

    class SeriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textNome: TextView = itemView.textViewListNome
        val textEpisodio: TextView = itemView.textViewListEpisodio
        val textId: TextView = itemView.textViewListId
        val imageSeries: ImageView = itemView.imageViewListEpisodio
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                    R.layout.recyclerview,
                parent,
                false
            )
        return SeriesViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val series = series[position]
        holder.textNome.text = series.nome
        holder.textEpisodio.text = series.episodioAtual
        holder.textId.text = series.id
        val fileReference = storageReference.child("Series/${series.id}.png")
        fileReference
            .getBytes(1024*1024)
            .addOnSuccessListener {         // it: ByteArray
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                holder.imageSeries.setImageBitmap(bitmap)
            }
            .addOnFailureListener {
                Log.i("RecyclerImageSeries", "${it.message}")
            }

        holder.itemView.setOnClickListener {
            actionClick(series)
        }

    }

    override fun getItemCount(): Int = series.size
}