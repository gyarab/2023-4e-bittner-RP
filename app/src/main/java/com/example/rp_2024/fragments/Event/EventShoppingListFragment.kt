package com.example.rp_2024.fragments.Event

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
import com.example.rp_2024.databaseStuff.EventShoppingLine
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentEventShoppingListBinding


class EventShoppingListFragment : Fragment() {
    private var listBinding: FragmentEventShoppingListBinding? = null

    private lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventShoppingListBinding.inflate(inflater, container, false)
        listBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        var event: Event? = null
        if (arguments != null) {
            if (EventAddFragmentArgs.fromBundle(requireArguments()).currentEvent != null) {
                val e: Event =
                    arguments.let { EventAddFragmentArgs.fromBundle(it!!).currentEvent!! }
                event = e
            }
        }



        binding.name.text = event!!.name

        binding.back.setOnClickListener {
            val action =
                EventShoppingListFragmentDirections.actionEventShoppingListFragmentToAddFragment(
                    event
                )
            findNavController().navigate(action)
        }

        val adapter = EventShoppingListAdapter(viewModel)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getShoppingLinesForEventLive(event.id).observe(viewLifecycleOwner, Observer{line ->
            adapter.setData(line)
        })

        binding.reload.setOnClickListener {
            viewModel.deleteEventShoppingLines(event.id)

            val people = viewModel.getEventAttendanceCount(event.id)
            val list = viewModel.getForEventShopping(event.id)
            val shopping: MutableMap<Pair<Int, String>, Int> =
                mutableMapOf<Pair<Int, String>, Int>()  //Map<Pair<ingredientId, measurement>, amount>
            for (p in list) {
                val keyPair = Pair(p.ingredientId, p.measurement)
                if (shopping.contains(keyPair)) {
                    shopping[keyPair] = shopping[keyPair]!! + p.amount * people
                } else {
                    shopping[keyPair] = p.amount * people
                }
            }

            for(p in shopping.toList()){
                val line = EventShoppingLine(0, event.id, p.first.first,  p.first.second, p.second, false)
                viewModel.upsertEventShoppingLine(line)
            }

        }
        return binding.root
    }

}