package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.EventAttendance
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databinding.EventAttendanceCustomRowBinding


class EventAttendanceAdapter(private val viewModel : MyViewModel, private val event: Event): RecyclerView.Adapter<EventAttendanceAdapter.MyViewHolder>() {

     var map: Map<Person, EventAttendance?> = emptyMap()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventAttendanceCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return map.size
    }


    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        with(holder){
            with(map.toList()[position]) {

                binding.number.text = (position+1).toString()
                binding.name.text = this.first.name.replaceFirstChar { this.first.name.first().uppercaseChar() }
                binding.surname.text = this.first.surname.replaceFirstChar { this.first.surname.first().uppercaseChar() }

                if(this.first.alias != ""){
                    binding.alias.text = this.first.alias.replaceFirstChar { this.first.alias.first().uppercaseChar() }
                    binding.alias.layoutParams.height = 50
                } else {
                    binding.alias.text = ""
                    binding.alias.layoutParams.height = 0
                }

                val s = this.first.status
                binding.status.text = when(s){
                    0 -> "dítě"
                    1 -> "instruktor"
                    2 -> "vedoucí"
                    3 -> "kmeňák"
                    else -> "neznámé"
                }

                if(this.second == null){
                    binding.radio.isChecked = true
                } else {
                    if(this.second!!.atends){
                        binding.radioYes.isChecked = true
                    } else {
                        binding.radioNo.isChecked = true
                    }
                }

                binding.radioYes.setOnClickListener{

                        var e :EventAttendance? = this.second
                        if (this.second == null) {
                            e = EventAttendance(0, event.id, this.first.id, true)
                        } else {
                            e!!.atends = true
                        }
                        viewModel.upsertEventAttendance(e!!)

                }
                binding.radioNo.setOnClickListener{

                        var e :EventAttendance? = this.second
                        if (this.second == null) {
                            e = EventAttendance(0, event.id, this.first.id, false)
                        } else {
                            e!!.atends = false
                        }
                        viewModel.upsertEventAttendance(e!!)

                }
                binding.radio.setOnClickListener{
                    if(this.second != null) {
                        viewModel.deleteEventAttendance(this.second!!)
                    }
                }
/*
                binding.number.text = (position+1).toString()
                binding.name.text = list.keys.toList()[position].name.replaceFirstChar { list.keys.toList()[position].name.first().uppercaseChar() }
                binding.surname.text = list.keys.toList()[position].surname.replaceFirstChar { list.keys.toList()[position].surname.first().uppercaseChar() }

                if(list.keys.toList()[position].alias != ""){
                    binding.alias.text = list.keys.toList()[position].alias.replaceFirstChar { list.keys.toList()[position].alias.first().uppercaseChar() }
                    binding.alias.layoutParams.height = 50
                }

                val s = list.keys.toList()[position].status
                binding.status.text = when(s){
                    0 -> "dítě"
                    1 -> "instruktor"
                    2 -> "vedoucí"
                    3 -> "kmeňák"
                    else -> "neznámé"
                }

                if(list[this.first] == null){
                    binding.radio.isChecked = true
                } else {
                    if(list[this.first]!!.atends){
                        binding.radioYes.isChecked = true
                    } else {
                        binding.radioNo.isChecked = true
                    }
                }

                binding.radioYes.setOnCheckedChangeListener{ _, isChecked ->
                    if(isChecked) {
                        if (list[this.first] == null) {
                            list.put(this.first, EventAttendance(0, event.id, this.first.id, true))
                        } else {
                            list[this.first]!!.atends = true
                        }
                        viewModel.upsertEventAttendance(list[this.first]!!)

                    }
                }
                binding.radioNo.setOnCheckedChangeListener{ _, isChecked ->
                    if(isChecked) {
                        if (list[this.first] == null) {
                            list.put(this.first, EventAttendance(0, event.id, this.first.id, false))
                        } else {
                            list[this.first]!!.atends = false
                        }
                        viewModel.upsertEventAttendance(list[this.first]!!)

                    }
                }
                binding.radio.setOnCheckedChangeListener{ _, isChecked ->
                    if(isChecked) {
                        viewModel.deleteEventAttendance(list[this.first]!!)
                        list[this.first] = null

                    }
                }

*/
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(attendance: Map<Person, EventAttendance?>){
        this.map = attendance
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: EventAttendanceCustomRowBinding): RecyclerView.ViewHolder(binding.root)


}