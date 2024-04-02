package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Dish
import com.example.rp_2024.databaseStuff.EventDish
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.EventDishCustomRowBinding

//adapter pro zobrazení jídel plánovaných na jednu akci
class EventDishListAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<EventDishListAdapter.MyViewHolder>() {

    var map: Map<EventDish, Dish> = emptyMap()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventDishCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                binding.name.text = this.second.name
                binding.note.text = this.second.note

                binding.delete.setOnClickListener{
                    viewModel.deleteEventDish(this.first)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(map: Map<EventDish, Dish>){
        this.map = map
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: EventDishCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}