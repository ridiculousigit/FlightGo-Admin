package binar.academy.flightgoadmin.view

import android.os.Bundle
import android.util.Log
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
class HomeFragment : Fragment(), TiketAdapter.onClickInterface {

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
        viewModel = ViewModelProvider(this)[AdminViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Auth
        viewModel.getRole().observe(viewLifecycleOwner){
            if (it != null){
                val name = "Halo, $it"
                binding.txtHeloAdmin.text = name
            }
        }

        //Show Data
        showData()

        //Customer
        binding.btnCustomer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_costumerFragment)
        }
        //Tambah Tiket
        binding.btnAddTiket.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addTiketFragment)
        }
    }

    private fun setUpRV(tiketResponse: List<TiketResponseItem>) {
        adapter = TiketAdapter(requireContext(), tiketResponse, this)
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun showData() {
        viewModel.getApiAllTic()
        viewModel.getLiveAllTic().observe(viewLifecycleOwner) { list ->
            Log.d("Data Tiket: ", list.toString())
            if (list != null) {
                setUpRV(list)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

    override fun onItemClick(list: TiketResponseItem) {
        Log.d("Item Clicked", "List Tiket : ${list.id}")
        val bundle = Bundle()
        bundle.putSerializable("dataTiket", list)
        findNavController().navigate(R.id.action_homeFragment_to_detailTiketFragment, bundle)
    }

}