package com.example.rp_2024.fragments.Dish

import android.app.AlertDialog
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
import com.example.rp_2024.databaseStuff.Dish
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentDishAddBinding
import com.example.rp_2024.databinding.FragmentDishAddIngredientDialogBinding
import com.example.rp_2024.databinding.FragmentDishAlertDialogBinding


class DishAddFragment : Fragment() {



    lateinit var dialogBinding: FragmentDishAlertDialogBinding

    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDishAddBinding.inflate(inflater, container, false)
        dialogBinding = FragmentDishAlertDialogBinding.inflate(inflater, container, false)


        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        var dishId = -1
        var dish: Dish? = null
        if(arguments != null){
            if(DishAddFragmentArgs.fromBundle(requireArguments()).currentDish != null){
                val d: Dish = arguments.let { DishAddFragmentArgs.fromBundle(it!!).currentDish!! }
                dishId = d.id
                dish = d
            }
        }



        val adapter = RecipeLineListAdapter(viewModel)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())


        binding.add.setOnClickListener{
            val builder = AlertDialog.Builder(context)

            val addIngredientDialogBinding = FragmentDishAddIngredientDialogBinding.inflate(inflater, container, false)

            builder.setTitle("vyberte ingredienci")
            builder.setView(addIngredientDialogBinding.root)
            builder.setNegativeButton("zrušit"){ dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val shower: AlertDialog = builder.show()

            val adapter = AddIngredientListAdapter(viewModel, dish!!, shower)
            val recycler = addIngredientDialogBinding.recyclerView
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
            adapter.setData(viewModel.getIngredientsOrderedByName)

        }

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }


        binding.name.setText(dish!!.name)

        viewModel.getRecipeLinesForDishByNameLive(dish.id).observe(viewLifecycleOwner, Observer{recipeLine ->
            adapter.setData(recipeLine)
        })


        return binding.root
    }

}