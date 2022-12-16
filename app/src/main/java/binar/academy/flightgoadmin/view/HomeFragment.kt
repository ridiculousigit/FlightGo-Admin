package binar.academy.flightgoadmin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import binar.academy.flightgoadmin.R
import binar.academy.flightgoadmin.adapter.TiketAdapter
import binar.academy.flightgoadmin.databinding.FragmentHomeBinding
import binar.academy.flightgoadmin.model.tiket.TiketResponseItem
import binar.academy.flightgoadmin.viewmodel.AdminViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : TiketAdapter
    private lateinit var viewModel: AdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[AdminViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Show Data
        showData()

        //Set RV
        adapter = TiketAdapter(ArrayList())
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //Customer
        findNavController().navigate(R.id.action_homeFragment_to_costumerFragment)
        //Tambah Tiket
        //findNavController().navigate(R.id.action_homeFragment_to_addTiketFragment)
    }

    private fun showData() {
        viewModel.getApiAllTic()
        viewModel.getLiveAllTic().observe(viewLifecycleOwner){
            if (it != null){
                adapter.setData(it as ArrayList<TiketResponseItem>)
                binding.rvHome.adapter = TiketAdapter(it)
                adapter = TiketAdapter(it)
                binding.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

}