package com.example.rp_2024.fragments.Person

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databinding.PersonCustomRowBinding


class PersonListAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<PersonListAdapter.MyViewHolder>() {

    private var personList = emptyList<Person>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PersonCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(personList[position]) {
                binding.number.text = (position+1).toString()
                binding.name.text = name
                binding.surname.text = surname
                val s = status
                binding.status.text = when(s){
                    0 -> "dítě"
                    1 -> "instruktor"
                    2 -> "vedoucí"
                    3 -> "kmeňák"
                    else -> "neznámé"
                }
                binding.delete.setOnClickListener{
                    val mId = motherId
                    val fId = fatherId


                    val l: List<Person> = viewModel.getAll
                    if(mId != -1) {
                        var motherOfSomeoneElse = false
                        for (p: Person in l) {
                            if (p.motherId == mId && p.id != id) {
                                motherOfSomeoneElse = true
                            }
                        }
                        if (!motherOfSomeoneElse) {
                            viewModel.deletePerson(viewModel.getPerson(mId))
                        }
                    }
                    if(fId != -1) {
                        var fatherOfSomeoneElse = false
                        for (p: Person in l) {
                            if (p.fatherId == fId && p.id != id) {
                                fatherOfSomeoneElse = true
                            }
                        }
                        if(!fatherOfSomeoneElse){
                            viewModel.deletePerson(viewModel.getPerson(fId))
                        }
                    }

                    viewModel.deletePerson(personList[position])

                    setData(personList.minus(personList[position]))
                }
                binding.edit.setOnClickListener{
                    val action = PersonListFragmentDirections.actionListFragmentToAddFragment(personList[position])
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(person: List<Person>){
        this.personList = person
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: PersonCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}