package com.example.mymarvelapp.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mymarvelapp.data.model.ItemMarvel
import com.example.mymarvelapp.databinding.FragmentDetailMarvelBinding
import com.example.mymarvelapp.ui.viewmodel.CharacterMarvelViewModel
import com.example.mymarvelapp.utils.Utils
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMarvelFragment : Fragment() {
    private var _binding: FragmentDetailMarvelBinding? = null
    private val binding get() = _binding!!
    private val args: DetailMarvelFragmentArgs by navArgs()
    private val characterMarvelViewModel: CharacterMarvelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMarvelBinding.inflate(inflater, container, false)
        binding.loadingCharacterMarvel.visibility = View.VISIBLE
        if (context?.let { Utils.isNetworkAvailable(it) } == true) {
            loadData()
        } else {
            configError("Offline, activate and press to select Character")
        }
        binding.textBack.text = "Back"
        binding.textBack.setOnClickListener {
            parentFragment?.findNavController()?.popBackStack()
        }
        return binding.root
    }

    private fun loadData() {
        characterMarvelViewModel.mutableGetCharacter.observe(
            viewLifecycleOwner,
            Observer { Character ->
                binding.loadingCharacterMarvel.visibility = View.GONE
                if (Character.code == 200) {
                    if (!Character.data?.result.isNullOrEmpty()) {
                        loadViewData(Character.data?.result?.get(0))
                    } else {
                        binding.warningTextView.visibility = View.VISIBLE
                        binding.warningTextView.text = "The character doesn't exist"
                    }
                } else {
                    configError("An error has occurred, press to select Character")
                }
            })
    }

    private fun loadViewData(character: ItemMarvel?) {
        binding.nameMarvelTextView.text = character?.name
        binding.descriptionMarvelTextView.text = if (!character?.description.isNullOrBlank()) {
            character?.description
        } else {
            "No description"
        }
        val image = character?.image?.path + "." + character?.image?.extension
        val imageHttps = image.replace("http", "https")
        Picasso.get().load(imageHttps).into(binding.marvelImageView)
    }

    private fun initService() {
        if (activity?.let { Utils.isNetworkAvailable(it) } == true) {
            characterMarvelViewModel.onCreate(args.idMarvel)
        }
    }

    private fun configError(error: String) {
        binding.loadingCharacterMarvel.visibility = View.GONE
        binding.warningTextView.visibility = View.VISIBLE
        binding.warningTextView.text = error
        binding.warningTextView.setOnClickListener {
            parentFragment?.findNavController()?.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}