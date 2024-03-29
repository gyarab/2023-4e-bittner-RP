package com.example.rp_2024.fragments.Dish

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Dish
import com.example.rp_2024.databaseStuff.Ingredient
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.RecipeLine
import com.example.rp_2024.databinding.AddIngredientCustomRowBinding


class AddIngredientListAdapter(private val viewModel : MyViewModel, private val dish: Dish, private val shower: AlertDialog): RecyclerView.Adapter<AddIngredientListAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AddIngredientCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(ingredientList[position]) {
                binding.number.text = (position+1).toString()
                binding.name.text = name
                binding.note.text = note

                binding.layout.setOnClickListener{
                    val lines = viewModel.getRecipeLinesForDishByName(dish.id)
                    var containsIngredient = false
                    for(l in lines){
                        if (l.ingredientId == this.id){
                            containsIngredient = true
                        }
                    }
                    if(!containsIngredient) {
                        viewModel.upsertRecipeLine(RecipeLine(0, dish.id, this.id, 0, ""))
                    }else{
                        //Toast.makeText(context, "ingredience už v receptu je", Toast.LENGTH_LONG).show()
                    }
                    shower.dismiss()
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(ingredient: List<Ingredient>){
        this.ingredientList = ingredient
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: AddIngredientCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}