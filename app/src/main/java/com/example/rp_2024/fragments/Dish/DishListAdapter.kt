package com.example.rp_2024.fragments.Dish

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Dish
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.RecipeLine
import com.example.rp_2024.databinding.DishCustomRowBinding


class DishListAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<DishListAdapter.MyViewHolder>() {

    private var dishList = emptyList<Dish>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DishCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dishList.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(dishList[position]) {
                binding.number.text = (position+1).toString()
                binding.name.text = name
                binding.note.text = note

                binding.delete.setOnClickListener{
                    viewModel.deleteDish(dishList[position])
                    setData(dishList.minus(dishList[position]))
                    val lines: List<RecipeLine> = viewModel.getRecipeLinesForDishByName(id)
                    for(l in lines){
                        viewModel.deleteRecipeLine(l)
                    }
                }
                binding.edit.setOnClickListener{
                    val action = DishListFragmentDirections.actionListFragmentToAddFragment(dishList[position])
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dish: List<Dish>){
        this.dishList = dish
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: DishCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}