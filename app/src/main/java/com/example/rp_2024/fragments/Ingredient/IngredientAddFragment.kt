package com.example.rp_2024.fragments.Ingredient

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rp_2024.R
import com.example.rp_2024.databaseStuff.Ingredient
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentIngredientAddBinding


class IngredientAddFragment : Fragment() {


    private var addBinding: FragmentIngredientAddBinding? = null

    private lateinit var viewModel: MyViewModel
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentIngredientAddBinding.inflate(inflater, container, false)
        addBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]


        var editedIngredientId = -1

        if(arguments != null){
            if(IngredientAddFragmentArgs.fromBundle(requireArguments()).currentIngredient != null){
                editedIngredientId = writeIngredientInfo(binding)
            }
        }

        viewModel.upsertIngredient(Ingredient(20, "mouka", ""))
        viewModel.upsertIngredient(Ingredient(21, "těstoviny", ""))
        viewModel.upsertIngredient(Ingredient(22, "brambory", ""))
        viewModel.upsertIngredient(Ingredient(23, "pepř", ""))
        viewModel.upsertIngredient(Ingredient(24, "česnek", ""))


        binding.button.setOnClickListener{
            if(binding.name.text.toString() != "") {
                upsertData(binding, editedIngredientId)
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
                Toast.makeText(requireContext(), "ingredience přidána", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(requireContext(), "vyplňte název ingredience", Toast.LENGTH_LONG).show()
            }

        }

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }



        return binding.root
    }

    private fun writeIngredientInfo(binding: FragmentIngredientAddBinding): Int {

            val i: Ingredient = arguments.let { IngredientAddFragmentArgs.fromBundle(it!!).currentIngredient!! }

            binding.name.setText(i.name)
            binding.note.setText(i.note)

            return i.id


    }

    @SuppressLint("SimpleDateFormat")
    private fun upsertData(binding: FragmentIngredientAddBinding, editedIngredientId: Int){
        val name = binding.name.text.toString().lowercase()
        val note = binding.note.text.toString()

            val ingredient = Ingredient(0, name, note)

            if (editedIngredientId != -1) {
                ingredient.id = editedIngredientId
                viewModel.upsertIngredient(ingredient)
            } else {

                viewModel.upsertIngredient(ingredient)

            }


    }



}