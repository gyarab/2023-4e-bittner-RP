package com.example.rp_2024.fragments.Event


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentEventAttendanceBinding


//adapter se seznamem členú a jejich účastí v recycler view
class EventAttendanceFragment : Fragment() {
    private var listBinding: FragmentEventAttendanceBinding? = null

    private lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventAttendanceBinding.inflate(inflater, container, false)
        listBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]




        var event: Event? = null
        if(arguments != null){
            if(EventAddFragmentArgs.fromBundle(requireArguments()).currentEvent != null){
                val e: Event = arguments.let { EventAddFragmentArgs.fromBundle(it!!).currentEvent!! }

                event = e
            }
        }

        val adapter = EventAttendanceAdapter(viewModel, event!!)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        binding.floatingActionButton.setOnClickListener{
            val action = EventAttendanceFragmentDirections.actionEventAttendanceFragmentToAddFragment(event)
            findNavController().navigate(action)
        }



        if (event != null) {
            viewModel.getAttendanceOrderedByAgeLive(event.id).observe(viewLifecycleOwner
            ) { attendancemap ->
                adapter.setData(attendancemap)
            }
        }

        return binding.root
    }

}