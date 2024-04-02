package com.example.rp_2024.fragments.Event


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentDishAlertDialogBinding
import com.example.rp_2024.databinding.FragmentEventListBinding

//fragment se seznamem všech akcí v recycler view
class EventListFragment : Fragment() {
    private var listBinding: FragmentEventListBinding? = null

    private lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEventListBinding.inflate(inflater, container, false)
        listBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val adapter = EventListAdapter(viewModel)
        val recycler = binding.recyclerView
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())


        //zobrazí dialog, načte od uživatele jméno akce a tu vytvoří
        binding.floatingActionButton.setOnClickListener{
            val dialogBinding = FragmentDishAlertDialogBinding.inflate(inflater, container, false)
            val builder = AlertDialog.Builder(context)

            builder.setTitle("vyplňte název výpravy")
            builder.setView(dialogBinding.root)
            builder.setPositiveButton("OK") { dialogInterface,
                                              i ->
                var event = Event(0, "", -1, -1, -1, -1, "")
                event.name = dialogBinding.name.text.toString()
                viewModel.upsertEvent(event)

            }
            builder.setNegativeButton("zrušit") {dialog, i ->
                dialog.dismiss()
            }
            builder.show()

        }



        viewModel.getEventsOrderedByDateLive.observe(viewLifecycleOwner, Observer{events ->
            adapter.setData(events)
        })

        return binding.root
    }

}