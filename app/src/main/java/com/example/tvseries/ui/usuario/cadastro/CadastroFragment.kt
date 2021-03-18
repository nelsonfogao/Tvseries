package com.example.tvseries.ui.usuario.cadastro

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
import kotlinx.android.synthetic.main.cadastro_fragment.*

class CadastroFragment : Fragment() {

    private lateinit var cadastroViewModel: CadastroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cadastroViewModel = ViewModelProvider(this).get(CadastroViewModel::class.java)

        cadastroViewModel.status.observe(viewLifecycleOwner, Observer {
            if (it)
                findNavController().popBackStack()
        })

        cadastroViewModel.msg.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank())
                showToast(it)

        })

        return inflater.inflate(R.layout.cadastro_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCadastroComSucesso.setOnClickListener {

            val senha = editTextCadastroSenha.text.toString()
            val resenha = editTextCadastroResenha.text.toString()

            if (senha == resenha) {
                val nome = editTextCadastroNome.text.toString()
                val email = editTextCadastroEmail.text.toString()
                cadastroViewModel.salvarCadastro(email, senha, nome)
            } else
                showToast("Senhas não conferem")
        }
    }

    private fun showToast(msg: String) {
        Toast
            .makeText(
                requireContext(),
                msg,
                Toast.LENGTH_LONG
            ).show()
    }
}