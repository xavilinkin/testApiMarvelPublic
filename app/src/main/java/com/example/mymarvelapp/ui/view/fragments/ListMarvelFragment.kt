package com.example.mymarvelapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymarvelapp.data.model.ItemMarvel
import com.example.mymarvelapp.databinding.FragmentListMarvelBinding
import com.example.mymarvelapp.ui.view.adapter.ListMarvelAdapter
import com.example.mymarvelapp.ui.view.fragments.listener.OnClickListMarvelListener
import com.example.mymarvelapp.ui.viewmodel.AllMarvelViewModel
import com.example.mymarvelapp.utils.Utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListMarvelFragment : Fragment() {
    private var _binding: FragmentListMarvelBinding? = null
    private val binding get() = _binding!!
    private val marvelViewModel: AllMarvelViewModel by viewModels()
    lateinit var listener: OnClickListMarvelListener
    lateinit var adapterMarvel: ListMarvelAdapter
    private val itemsMarvels = mutableListOf<ItemMarvel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListMarvelBinding.inflate(inflater, container, false)
        setListener()
        initRecyclerView()
        binding.titleListTextView.text = "List of Marvel Characters"
        binding.loadingMarvels.visibility = View.VISIBLE
        if (context?.let { isNetworkAvailable(it) } == true) {
            configDataService()
        } else {
            configError("Offline, activate and press to recharge")
        }
        return binding.root
    }

    private fun configDataService() {
        marvelViewModel.mutableGetMarvels.observe(viewLifecycleOwner, Observer { ModelMarvel ->
            val listMarvel = ModelMarvel?.data?.result ?: emptyList()
            binding.loadingMarvels.visibility = View.GONE
            if (ModelMarvel?.code == 200) {
                if (listMarvel.isNotEmpty()) {
                    itemsMarvels.clear()
                    itemsMarvels.addAll(listMarvel)
                    adapterMarvel.notifyDataSetChanged()
                    binding.warningTextView.visibility = View.GONE
                } else {
                    binding.warningTextView.visibility = View.VISIBLE
                    binding.warningTextView.text = "Empty list"
                }
            } else {
                configError("An error has occurred, press to reload")
            }
        })
    }

    private fun configError(error: String) {
        binding.loadingMarvels.visibility = View.GONE
        binding.warningTextView.visibility = View.VISIBLE
        binding.warningTextView.text = error
        binding.warningTextView.setOnClickListener {
            if (activity?.let { isNetworkAvailable(it) } == true) {
                refreshCurrentFragment()
            }
        }
    }

    private fun refreshCurrentFragment() {
        val id = findNavController().currentDestination?.id
        findNavController().popBackStack(id!!, true)
        findNavController().navigate(id)
    }

    private fun initRecyclerView() {
        adapterMarvel = ListMarvelAdapter(itemsMarvels, listener)
        binding.itemsListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.itemsListRecyclerView.adapter = adapterMarvel
    }

    private fun setListener() {
        listener = object : OnClickListMarvelListener {
            override fun goToCharacter(id: String) {
                val passId =
                    ListMarvelFragmentDirections.actionListMarvelFragmentToDetailMarvelFragment(id)
                findNavController().navigate(passId)
            }
        }
    }

    private fun initService() {
        if (activity?.let { isNetworkAvailable(it) } == true) {
            marvelViewModel.onCreate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}