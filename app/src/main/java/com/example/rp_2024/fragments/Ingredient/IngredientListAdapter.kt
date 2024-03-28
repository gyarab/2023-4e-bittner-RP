package com.example.rp_2024.fragments.Ingredient

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Ingredient
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.IngredientCustomRowBinding


class IngredientListAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<IngredientListAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<Ingredient>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = IngredientCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(ingredientList[position]) {
                binding.number.text = (position+1).toString()
                binding.name.text = name
                binding.note.text = note

                binding.delete.setOnClickListener{

                    viewModel.deleteIngredient(ingredientList[position])

                    setData(ingredientList.minus(ingredientList[position]))
                }
                binding.edit.setOnClickListener{

                    val action = IngredientListFragmentDirections.actionListFragmentToAddFragment(ingredientList[position])
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(ingredient: List<Ingredient>){
        this.ingredientList = ingredient
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: IngredientCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}