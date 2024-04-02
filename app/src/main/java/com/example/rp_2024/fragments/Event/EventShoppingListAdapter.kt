package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.EventShoppingLine
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.EventShoppingLineCustomRowBinding

//adapter pro zobrazení řádek v nákupním seznamu akce
class EventShoppingListAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<EventShoppingListAdapter.MyViewHolder>() {

    var list: List<EventShoppingLine> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventShoppingLineCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                binding.ingredient.text = viewModel.getIngredient(this.ingredientId).name
                binding.amount.text = this.amount.toString()
                binding.measurement.text = this.measurement
                binding.check.isChecked = this.bought

                //každá změna je ihned uložena do databáze
                binding.check.setOnCheckedChangeListener{ _, isChecked ->
                    this.bought = isChecked
                    viewModel.upsertEventShoppingLine(this)
                }
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<EventShoppingLine>){
        this.list = list
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: EventShoppingLineCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}