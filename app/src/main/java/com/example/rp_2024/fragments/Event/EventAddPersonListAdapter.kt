package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databinding.EventAddPersonCustomRowBinding

//adapter pro přidání organizátora, nebo zodpovědného
class EventAddPersonListAdapter(private val viewModel : MyViewModel, private val event: Event, private val type: Int, private val shower: AlertDialog): RecyclerView.Adapter<EventAddPersonListAdapter.MyViewHolder>() {

    private var list = emptyList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventAddPersonCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(list[position]) {
                binding.number.text = (position+1).toString()
                binding.name.text = name.replaceFirstChar { name.first().uppercaseChar() }
                binding.surname.text = surname.replaceFirstChar { surname.first().uppercaseChar() }

                if(alias != ""){
                    binding.alias.text = alias.replaceFirstChar { alias.first().uppercaseChar() }
                    binding.alias.layoutParams.height = 50
                }

                if(type == 1){
                    if(event.adminId == id){
                        binding.view.setBackgroundColor(Color.parseColor("#009900"))
                    }
                }else{
                    if(event.adultId == id){
                        binding.view.setBackgroundColor(Color.parseColor("#009900"))
                    }
                }

                val s = status
                binding.status.text = when(s){
                    0 -> "dítě"
                    1 -> "instruktor"
                    2 -> "vedoucí"
                    3 -> "kmeňák"
                    else -> "neznámé"
                }
                binding.layout.setOnClickListener{
                    if(type==1){
                        event.adminId = this.id
                        viewModel.upsertEvent(event)
                    }else {
                        event.adultId = this.id
                        viewModel.upsertEvent(event)
                    }



                    shower.dismiss()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(people: List<Person>){
        this.list = people
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: EventAddPersonCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}