package binar.academy.flightgoadmin.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler(Looper.getMainLooper()).postDelayed({
            Navigation.findNavController(binding.root).navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }, 3000L)
    }
}