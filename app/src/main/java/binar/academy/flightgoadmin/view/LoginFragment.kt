package binar.academy.flightgoadmin.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import binar.academy.flightgoadmin.MainActivity
import binar.academy.flightgoadmin.databinding.FragmentLoginBinding
import binar.academy.flightgoadmin.viewmodel.AdminViewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private var savedEmail: String = ""
    private var savedPassword: String = ""
    lateinit var viewModel: AdminViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[AdminViewModel::class.java]
    }

    private fun setupView() {
        viewModel.callDataAdmin(viewLifecycleOwner)
        binding.btnSignIn.setOnClickListener {
            loginAction()
        }

        viewModel.livedataEmail.observe(viewLifecycleOwner, ({
            savedEmail = "admin@gmail.com"
        }))

        viewModel.livedataPassword.observe(viewLifecycleOwner, ({
            savedPassword = "123456"
        }))
    }

    private fun loginAction() {
        val email = binding.adminEmail.text.toString()
        val password = binding.adminPassword.text.toString()
        when {
            email == "" -> {
                binding.adminEmail.error = "Please fill out this field."
            }
            password == "" -> {
                binding.adminPassword.error = "Please fill out this field."
            }
            email != savedEmail -> {
                Toast.makeText(mContext, "Incorrect Username !", Toast.LENGTH_SHORT).show()
            }
            password != savedPassword -> {
                Toast.makeText(mContext, "Incorrect Password", Toast.LENGTH_SHORT).show()
            }
            email == savedEmail && password == savedPassword -> {
                viewModel.saveLoginStatus(true)
                startActivity(Intent(mContext, MainActivity::class.java))
                activity?.finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}