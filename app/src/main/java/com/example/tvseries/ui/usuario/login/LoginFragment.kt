package com.example.tvseries.ui.usuario.login

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
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginViewModel.status.observe(viewLifecycleOwner, Observer {
            if (it) {

                findNavController().navigate(R.id.listFragment)
            }
        })
        loginViewModel.msg.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank())
                Toast
                    .makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_LONG
                    ).show()
        })

        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonAcessar.setOnClickListener {
            val email = editTextEmailAddress.text.toString()
            val senha = editTextPassword.text.toString()
            loginViewModel.verificarCredenciais(email, senha)
        }

        buttonCadastrar.setOnClickListener {
            findNavController().navigate(R.id.cadastroFragment)
        }
    }
}