package com.example.rp_2024.fragments.Event

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
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentEventAddDishDialogBinding
import com.example.rp_2024.databinding.FragmentEventDishListBinding

//fragment se seznamem jídel plánovaných pro tuto akci v recycler view
class EventDishListFragment : Fragment() {
    private var listBinding: FragmentEventDishListBinding? = null

    private lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventDishListBinding.inflate(inflater, container, false)
        listBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        var event: Event? = null
        if(arguments != null){
            if(EventAddFragmentArgs.fromBundle(requireArguments()).currentEvent != null){
                val e: Event = arguments.let { EventAddFragmentArgs.fromBundle(it!!).currentEvent!! }
                event = e
            }
        }

        val adapter = EventDishListAdapter(viewModel)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.name.text = event!!.name

        binding.back.setOnClickListener{
            val action = EventDishListFragmentDirections.actionDishListFragmentToAddFragment(event)
            findNavController().navigate(action)
        }


        //přidání jídla
        //zobrazí dialog se všemi jídli v recycler view, uživatel si vybere
        binding.floatingActionButton.setOnClickListener{
            val dialogBinding = FragmentEventAddDishDialogBinding.inflate(inflater, container, false)
            val builder = AlertDialog.Builder(context)

            builder.setTitle("vyberte jídlo")
            builder.setView(dialogBinding.root)
            builder.setNegativeButton("zrušit") {dialog, i ->
                dialog.dismiss()
            }
            val shower: AlertDialog = builder.show()

            val adapter = EventAddDishListAdapter(viewModel, event!!, shower)
            val recycler = dialogBinding.recyclerView
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
            adapter.setData(viewModel.getDishesOrderedByName)


        }



        viewModel.getEventDishes(event!!.id).observe(viewLifecycleOwner, Observer{map ->
            adapter.setData(map)
        })

        return binding.root
    }

}