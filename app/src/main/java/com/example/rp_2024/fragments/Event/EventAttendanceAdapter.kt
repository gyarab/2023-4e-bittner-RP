package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.EventAttendance
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.EventAttendanceCustomRowBinding


class EventAttendanceAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<EventAttendanceAdapter.MyViewHolder>() {

    private var list = emptyList<EventAttendance>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventAttendanceCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(list[position]) {
                val p = viewModel.getPerson(this.personId)
                binding.number.text = (position+1).toString()
                binding.name.text = p.name.replaceFirstChar { p.name.first().uppercaseChar() }
                binding.surname.text = p.surname.replaceFirstChar { p.surname.first().uppercaseChar() }

                if(p.alias != ""){
                    binding.alias.text = p.alias.replaceFirstChar { p.alias.first().uppercaseChar() }
                    binding.alias.layoutParams.height = 50
                }

                val s = p.status
                binding.status.text = when(s){
                    0 -> "dítě"
                    1 -> "instruktor"
                    2 -> "vedoucí"
                    3 -> "kmeňák"
                    else -> "neznámé"
                }
                binding.radioYes.setOnClickListener{

                }

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(attendance: List<EventAttendance>){
        this.list = attendance
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: EventAttendanceCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}