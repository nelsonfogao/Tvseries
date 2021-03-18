package com.example.tvseries.ui.form

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tvseries.R
import com.example.tvseries.database.AppUtil
import com.example.tvseries.database.SeriesFirestoreDao
import com.example.tvseries.model.Series
import kotlinx.android.synthetic.main.form_series_fragment.*

class FormSeriesFragment : Fragment() {

    private lateinit var formSeriesViewModel: FormSeriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.form_series_fragment, container, false)

        val application = requireActivity().application
        val formSeriesViewModelFactory =
            FormSeriesViewModelFactory(SeriesFirestoreDao(), application)

        formSeriesViewModel = ViewModelProvider(
            this, formSeriesViewModelFactory)
            .get(FormSeriesViewModel::class.java).apply {
                setUpMsgObserver(this)
                setUpStatusObserver(this)
                setUpImagemSeriesObserver(this)
            }

        formSeriesViewModel.series.observe(viewLifecycleOwner, Observer {
            if (it != null){
                preencherFormulario(it)
            }
        })

        return view
    }

    private fun setUpStatusObserver(formSeriesViewModel: FormSeriesViewModel) {
        formSeriesViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status)
                findNavController().popBackStack()
        })
    }
    private fun setUpMsgObserver(formSeriesViewModel: FormSeriesViewModel) {
        formSeriesViewModel.msg.observe(viewLifecycleOwner, Observer { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun setUpImagemSeriesObserver(formSeriesViewModel: FormSeriesViewModel) {
        formSeriesViewModel.imagemSeries.observe(viewLifecycleOwner, Observer { imagemSeries ->
            if (imagemSeries != null) {
                imageViewFormEpisodio.setImageURI(imagemSeries)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AppUtil.serieSelecionada != null) {
            editTextFormId.visibility = View.GONE
            formSeriesViewModel.selectSerie(AppUtil.serieSelecionada!!.id!!)
        }

        else {
            editTextFormId.visibility = View.VISIBLE
        }
        buttonFormSalvar.setOnClickListener {

            val episodioatual = editTextFormEpisodio.text.toString()
            val categoria = editTextFormCategoria.text.toString()
            val nome = editTextFormNome.text.toString()
            val id = editTextFormId.text.toString()
            formSeriesViewModel.salvarSeries(episodioatual,  categoria, nome, id)
        }

        imageViewFormEpisodio.setOnClickListener {
            selecionarImagem()
        }


        floatingActionButtonDelete.setOnClickListener{
            formSeriesViewModel.deletarSeries(AppUtil.serieSelecionada!!)
        }

    }
    private fun preencherFormulario(series: Series){
        editTextFormNome.setText(series.nome)
        editTextFormEpisodio.setText(series.episodioAtual)
        editTextFormCategoria.setText(series.categoria)
        editTextFormId.setText(series.id)
        formSeriesViewModel.setUpImagemSeries(series.id!!)
    }

    private fun limparFormulario() {
        editTextFormNome.setText("")
        editTextFormCategoria.setText("")
        editTextFormEpisodio.setText("")
    }
    private fun selecionarImagem(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 2){
                val photo: Uri = data!!.data!!
                imageViewFormEpisodio.setImageURI(photo)
                formSeriesViewModel.setImagemSeries(photo)
            }
        }
    }
}