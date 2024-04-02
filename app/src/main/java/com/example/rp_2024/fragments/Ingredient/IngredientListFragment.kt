package com.example.rp_2024.fragments.Ingredient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rp_2024.R
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentIngredientListBinding

//fragment se seznamem ingrediencí v recycler view

class IngredientListFragment : Fragment() {
    private var listBinding: FragmentIngredientListBinding? = null

    private lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentIngredientListBinding.inflate(inflater, container, false)
        listBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val adapter = IngredientListAdapter(viewModel)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())



        //naviguje na fragment na přidání ingredience
        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_listFragment_to_addFragment, null)
        }


        //nastavuje data adapteru pokaždé, když se v databázi změní
        viewModel.getIngredientsOrderedByNameLive.observe(viewLifecycleOwner, Observer{ ingredient ->
            adapter.setData(ingredient)
        })

        return binding.root
    }

}