package com.example.tvseries.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tvseries.R
import com.example.tvseries.RecyclerListSeries
import com.example.tvseries.database.AppUtil
import com.example.tvseries.database.SeriesFirestoreDao
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //verificarUsuario()

        val view =  inflater.inflate(R.layout.list_fragment, container, false)

        val listSeriesViewModelFactory = ListSeriesViewModelFactory(SeriesFirestoreDao())
        listViewModel = ViewModelProvider(this, listSeriesViewModelFactory)
                .get(ListViewModel::class.java)


        listViewModel.series.observe(viewLifecycleOwner, Observer {
            recyclerViewListSeries.adapter = RecyclerListSeries(it) {
                    AppUtil.serieSelecionada = it
                    findNavController().navigate(R.id.formSeriesFragment)
            }
            recyclerViewListSeries.layoutManager = LinearLayoutManager(requireContext())
        })
        listViewModel.atualizarListaSeries()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        floatingActionButton.setOnClickListener {
            AppUtil.serieSelecionada = null
            findNavController().navigate(R.id.formSeriesFragment)
        }
    }

//    fun verificarUsuario(){
//        if (UsuarioFirebaseDao.firebaseAuth.currentUser == null)
//            findNavController().popBackStack()
//    }

}