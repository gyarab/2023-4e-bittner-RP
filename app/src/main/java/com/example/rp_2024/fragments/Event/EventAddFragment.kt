package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rp_2024.R
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.FragmentDishAlertDialogBinding
import com.example.rp_2024.databinding.FragmentEventAddBinding
import java.text.SimpleDateFormat


class EventAddFragment : Fragment() {



    lateinit var dialogBinding: FragmentDishAlertDialogBinding

    private lateinit var viewModel: MyViewModel

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEventAddBinding.inflate(inflater, container, false)
        dialogBinding = FragmentDishAlertDialogBinding.inflate(inflater, container, false)


        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        var eventId = -1
        var event: Event? = null
        if(arguments != null){
            if(EventAddFragmentArgs.fromBundle(requireArguments()).currentEvent != null){
                val e: Event = arguments.let { EventAddFragmentArgs.fromBundle(it!!).currentEvent!! }
                eventId = e.id
                event = e
            }
        }



        binding.name.setText(event!!.name)
        binding.note.setText(event.note)

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
        if(event.adminId != -1){
            val p = viewModel.getPerson(event.adminId)
            binding.admin.text = p.name + " " + p.alias + " " + p.surname
        }
        if(event.adultId != -1){
            val p = viewModel.getPerson(event.adultId)
            binding.adult.text = p.name + " " + p.alias + " " + p.surname
        }


        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

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