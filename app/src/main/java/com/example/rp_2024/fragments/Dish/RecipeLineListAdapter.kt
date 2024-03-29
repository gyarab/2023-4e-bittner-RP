package com.example.rp_2024.fragments.Dish

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Ingredient
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.RecipeLine

import com.example.rp_2024.databinding.FragmentIngredientAlertDialogBinding
import com.example.rp_2024.databinding.RecipeLineCustomRowBinding


class RecipeLineListAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<RecipeLineListAdapter.MyViewHolder>() {

    private var linesList = emptyList<RecipeLine>()
    lateinit var dialogBinding: FragmentIngredientAlertDialogBinding
    lateinit var parent: ViewGroup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecipeLineCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        dialogBinding = FragmentIngredientAlertDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        this.parent = parent
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return linesList.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(linesList[position]) {
                var ingredient: Ingredient = viewModel.getIngredient(ingredientId)
                binding.number.text = (position+1).toString()
                binding.ingredient.text = ingredient.name
                binding.amount.text = amount.toString()
                binding.measurement.text = measurement

                binding.delete.setOnClickListener{
                    viewModel.deleteRecipeLine(linesList[position])
                    setData(linesList.minus(linesList[position]))
                }
                binding.edit.setOnClickListener{
                    edit(ingredient, this)

                }
            }
        }
    }

    fun edit(ingredient: Ingredient, line: RecipeLine){
        val builder = AlertDialog.Builder(parent.context)

        builder.setTitle(ingredient.name)

        dialogBinding.amount.hint = line.amount.toString()
        dialogBinding.measurement.hint = line.measurement
        builder.setView(dialogBinding.root)

        builder.setPositiveButton("OK") { dialogInterface, i ->
            if(dialogBinding.amount.text.toString() != "" ){
                line.amount = dialogBinding.amount.text.toString().toInt()
            }
            if( dialogBinding.measurement.text.toString() != "") {
                line.measurement = dialogBinding.measurement.text.toString()
            }
            viewModel.upsertRecipeLine(line)
            setData(linesList)
            Toast.makeText(parent.context, "upraveno", Toast.LENGTH_LONG).show()
        }
        builder.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(line: List<RecipeLine>){
        this.linesList = line
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: RecipeLineCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}