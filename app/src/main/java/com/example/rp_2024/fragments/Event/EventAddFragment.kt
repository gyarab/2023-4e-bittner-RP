package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rp_2024.R
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databinding.FragmentDishAlertDialogBinding
import com.example.rp_2024.databinding.FragmentEventAddBinding
import com.example.rp_2024.databinding.FragmentEventAddPersonDialogBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate

//fragment na upravování informací o akci
class EventAddFragment : Fragment() {



    lateinit var dialogBinding: FragmentDishAlertDialogBinding

    private lateinit var viewModel: MyViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventAddBinding.inflate(inflater, container, false)
        dialogBinding = FragmentDishAlertDialogBinding.inflate(inflater, container, false)


        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        //aplikace nikdy nenaviguje na tento fragment bez argumentu, proto bu vždy v promněnné event měla skončit momentálně upravovaná akce
        var eventId = -1
        var event: Event? = null
        if(arguments != null){
            if(EventAddFragmentArgs.fromBundle(requireArguments()).currentEvent != null){
                val e: Event = arguments.let { EventAddFragmentArgs.fromBundle(it!!).currentEvent!! }
                eventId = e.id
                event = e
            }
        }


        var current = LocalDate.now()
        current = current.minusYears(18)


        //vygeneruje počty kategorií pro koupi lístkú, podle účasti v databázi
        val unix = current.toEpochDay()*86400000
        binding.children.text = viewModel.getChildrenCountForEvent(event!!.id, unix).toString()
        binding.students.text = viewModel.getStudentCountForEvent(event!!.id, unix).toString()
        binding.adults.text = viewModel.getAdultCountForEvent(event!!.id, unix).toString()

        binding.name.setText(event!!.name)
        binding.note.setText(event.note)

        //pokud není čas začátku v databázi -1 (moje iniciační hodnota, bude když byl event právě vytvořen a je poprvé upravován)
        //přepočte Unix timestamp na čas a ten nastaví na NumberPicker View komponenty
        if(event.start.toInt() != -1){
            val sdf = java.text.SimpleDateFormat("yyyyMMddhhmm")
            val date = java.util.Date(event.start)
            val s = sdf.format(date)
            binding.startDayPicker.value = s.subSequence(6, 8).toString().toInt()
            binding.startMonthPicker.value = s.subSequence(4, 6).toString().toInt()
            binding.startYearPicker.value = s.subSequence(0, 4).toString().toInt()
            binding.startHourPicker.value = s.subSequence(8, 10).toString().toInt()
            binding.startMinutePicker.value = s.subSequence(10, 12).toString().toInt()

        }
        if(event.end.toInt() != -1){
            val sdf = java.text.SimpleDateFormat("yyyyMMddhhmm")
            val date = java.util.Date(event.end)
            val s = sdf.format(date)
            binding.endDayPicker.value = s.subSequence(6, 8).toString().toInt()
            binding.endMonthPicker.value = s.subSequence(4, 6).toString().toInt()
            binding.endYearPicker.value = s.subSequence(0, 4).toString().toInt()
            binding.endHourPicker.value = s.subSequence(8, 10).toString().toInt()
            binding.endMinutePicker.value = s.subSequence(10, 12).toString().toInt()
        }

        //inicializuje observer, který aktualizuje jménoorganizátora pokaždé když dojde v databázi ke zmněně
        val adminObserver = Observer<Person?> { p ->
            if (p != null) {
                if (p.alias == "") {
                    p.alias = " "
                }
                binding.admin.text = buildString {
                    append(p.name.replaceFirstChar {
                        p.name.first().uppercaseChar().toString()
                    })
                    append(" ")
                    append(p.alias.replaceFirstChar {
                        p.alias.first().uppercaseChar().toString()
                    })
                    append(" ")
                    append(p.surname.replaceFirstChar {
                        p.surname.first().uppercaseChar().toString()
                    })
                }
            }
        }
        //spustí vedlejší coroutine, kde se každé půl sekundy aktualizuje kdo je organizátor
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                while (true) {
                    viewModel.getPersonLive(event.adminId).observe(viewLifecycleOwner, adminObserver)
                    delay(500)
                }
            }
        }

        //inicializuje observer, který aktualizuje jméno zodpovědného dospělého pokaždé když dojde v databázi ke zmněně
        val adultObserver = Observer<Person?> { p ->
            if (p != null) {
                if (p.alias == "") {
                    p.alias = " "
                }
                binding.adult.text = buildString {
                    append(p.name.replaceFirstChar {
                        p.name.first().uppercaseChar().toString()
                    })
                    append(" ")
                    append(p.alias.replaceFirstChar {
                        p.alias.first().uppercaseChar().toString()
                    })
                    append(" ")
                    append(p.surname.replaceFirstChar {
                        p.surname.first().uppercaseChar().toString()
                    })
                }
            }
        }

        //spustí vedlejší coroutine, kde se každé půl sekundy aktualizuje kdo je zodpovědný
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                while (true) {
                    viewModel.getPersonLive(event.adultId).observe(viewLifecycleOwner, adultObserver)
                    delay(500)
                }
            }
        }

        //naviguje zpět na seznam
        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        //naviguje na fragment pro upravení účasti na akci
        binding.editAttendance.setOnClickListener{
            val action = EventAddFragmentDirections.actionAddFragmentToEventAttendanceFragment(event)
            findNavController().navigate(action)
        }

        //naviguje na fragment pro výběr plánovaných jídel na akci
        binding.editDishes.setOnClickListener{
            val action = EventAddFragmentDirections.actionAddFragmentToDishListFragment(event)
            findNavController().navigate(action)
        }

        //naviguje na fragment s nákupním seznamem
        binding.editShoppingList.setOnClickListener{
            val action = EventAddFragmentDirections.actionAddFragmentToEventShoppingListFragment(event)
            findNavController().navigate(action)
        }

        //zobrazí dialog s validními členy (podle statusu) v recycler view
        binding.editAdmin.setOnClickListener{
            val builder = AlertDialog.Builder(context)

            val addPersonDialogBinding = FragmentEventAddPersonDialogBinding.inflate(inflater, container, false)

            builder.setTitle("vyberte organizátora")
            builder.setView(addPersonDialogBinding.root)
            builder.setNegativeButton("zrušit"){ dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val shower: AlertDialog = builder.show()

            val adapter = EventAddPersonListAdapter(viewModel, event, 1, shower)
            val recycler = addPersonDialogBinding.recyclerView
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
            adapter.setData(viewModel.getAdultsAndInstructors)
        }

        //zobrazí dialog s validními členy (podle statusu) v recycler view, adapter je stejný, jako pro výběr organizátora, jen dostane jiná data
        binding.editAdult.setOnClickListener{
            val builder = AlertDialog.Builder(context)

            val addPersonDialogBinding = FragmentEventAddPersonDialogBinding.inflate(inflater, container, false)

            builder.setTitle("vyberte zodpovědného dospělého")
            builder.setView(addPersonDialogBinding.root)
            builder.setNegativeButton("zrušit"){ dialogInterface, i ->
                dialogInterface.dismiss()
            }
            val shower: AlertDialog = builder.show()

            val adapter = EventAddPersonListAdapter(viewModel, event, 2, shower)
            val recycler = addPersonDialogBinding.recyclerView
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(requireContext())
            adapter.setData(viewModel.getAdults)
        }

        //uloží data, která se neukládají rovnou
        //tj. název, časy a poznámka
        binding.button.setOnClickListener{
            event.name = binding.name.text.toString()
            event.note = binding.note.text.toString()

            var startday = binding.startDayPicker.value.toString()
            var startmonth = binding.startMonthPicker.value.toString()
            val startyear = binding.startYearPicker.value.toString()
            var starthour = binding.startHourPicker.value.toString()
            var startminute = binding.startMinutePicker.value.toString()

            if(startday.length == 1) {
                startday = "0$startday"
            }
            if(startmonth.length == 1) {
                startmonth = "0$startmonth"
            }
            if(starthour.length == 1) {
                starthour = "0$starthour"
            }
            if(startminute.length == 1) {
                startminute = "0$startminute"
            }

            val startdate = startday + startmonth + startyear + starthour + startminute
            var sdate = SimpleDateFormat("ddMMyyyyhhmm").parse(startdate)
            event.start = sdate.time

            var endday = binding.endDayPicker.value.toString()
            var endmonth = binding.endMonthPicker.value.toString()
            val endyear = binding.endYearPicker.value.toString()
            var endhour = binding.endHourPicker.value.toString()
            var endminute = binding.endMinutePicker.value.toString()

            if(endday.length == 1) {
                endday = "0$endday"
            }
            if(endmonth.length == 1) {
                endmonth = "0$endmonth"
            }
            if(endhour.length == 1) {
                endhour = "0$endhour"
            }
            if(endminute.length == 1) {
                endminute = "0$endminute"
            }

            val enddate = endday + endmonth + endyear + endhour + endminute
            var edate = SimpleDateFormat("ddMMyyyyhhmm").parse(enddate)
            event.end = edate.time

            //adminId & adultId


            viewModel.upsertEvent(event)

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }




        return binding.root
    }

}