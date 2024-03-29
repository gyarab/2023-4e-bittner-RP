package com.example.rp_2024.fragments.Dish

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rp_2024.databaseStuff.Dish
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentDishAlertDialogBinding
import com.example.rp_2024.databinding.FragmentDishListBinding



class DishListFragment : Fragment() {
    private var listBinding: FragmentDishListBinding? = null

    private lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDishListBinding.inflate(inflater, container, false)
        listBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val adapter = DishListAdapter(viewModel)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())



        binding.floatingActionButton.setOnClickListener{
            val dialogBinding = FragmentDishAlertDialogBinding.inflate(inflater, container, false)
            val builder = AlertDialog.Builder(context)

            builder.setTitle("vyplňte název jídla")
            builder.setView(dialogBinding.root)
            builder.setPositiveButton("OK") { dialogInterface,
                                              i ->
                var dish = Dish(0, "", "")
                dish.name = dialogBinding.name.text.toString()
                viewModel.upsertDish(dish)

            }
            builder.setNegativeButton("zrušit") {dialog, i ->
                dialog.dismiss()
            }
            builder.show()
        }



        viewModel.getDishesOrderedByName.observe(viewLifecycleOwner, Observer{dish ->
            adapter.setData(dish)
        })

        return binding.root
    }

}