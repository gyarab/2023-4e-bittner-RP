package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Dish
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.EventDish
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.EventAddDishCustomRowBinding


class EventAddDishListAdapter(private val viewModel : MyViewModel, private val event: Event, private val shower: AlertDialog): RecyclerView.Adapter<EventAddDishListAdapter.MyViewHolder>() {

    var list: List<Dish> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventAddDishCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(list[position]) {
                binding.number.text = (position+1).toString()
                binding.name.text = this.name
                binding.note.text = this.note

                binding.layout.setOnClickListener{
                    viewModel.upsertEventDish(EventDish(0, event.id, this.id))
                    shower.dismiss()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Dish>){
        this.list = list
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: EventAddDishCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}