package com.example.rp_2024.fragments.Event

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.EventShoppingLine
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databinding.EventCustomRowBinding


class EventListAdapter(private val viewModel : MyViewModel): RecyclerView.Adapter<EventListAdapter.MyViewHolder>() {

    private var list = emptyList<Event>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = EventCustomRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
                binding.name.text = name

                var time = ""

                if(this.start.toInt() != -1){
                    val sdf = java.text.SimpleDateFormat("yyyyMMddhhmm")
                    val date = java.util.Date(this.start)
                    val s = sdf.format(date)
                    var Day = s.subSequence(6, 8).toString()
                    var Month = s.subSequence(4, 6).toString()
                    val Year = s.subSequence(0, 4).toString()
                    var Hour = s.subSequence(8, 10).toString()
                    var Minute = s.subSequence(10, 12).toString()
                    if(Day.first() == '0'){ Day = Day.subSequence(1, 2).toString() }
                    if(Month.first() == '0'){ Month = Month.subSequence(1, 2).toString() }
                    if(Hour.first() == '0'){ Hour = Hour.subSequence(1, 2).toString() }
                    if(Minute.first() == '0'){ Minute = Minute.subSequence(1, 2).toString() }

                    time += "$Day.$Month - "
                }
                if(this.end.toInt() != -1){
                    val sdf = java.text.SimpleDateFormat("yyyyMMddhhmm")
                    val date = java.util.Date(this.end)
                    val s = sdf.format(date)
                    var Day = s.subSequence(6, 8).toString()
                    var Month = s.subSequence(4, 6).toString()
                    val Year = s.subSequence(0, 4).toString()
                    var Hour = s.subSequence(8, 10).toString()
                    var Minute = s.subSequence(10, 12).toString()
                    if(Day.first() == '0'){ Day = Day.subSequence(1, 2).toString() }
                    if(Month.first() == '0'){ Month = Month.subSequence(1, 2).toString() }
                    if(Hour.first() == '0'){ Hour = Hour.subSequence(1, 2).toString() }
                    if(Minute.first() == '0'){ Minute = Minute.subSequence(1, 2).toString() }
                    time+="$Day.$Month"
                }
                binding.time.text = time

                binding.delete.setOnClickListener{
                    viewModel.deleteEvent(list[position])
                    setData(list.minus(list[position]))
                    val shoppingLines: List<EventShoppingLine> = viewModel.getShoppingLinesForEvent(id)
                    val attendance = viewModel.getAttendanceForEvent(id)
                    val dishes = viewModel.getEventDishesForEvent(id)

                    for(l in shoppingLines){
                        viewModel.deleteEventShoppingLine(l)
                    }
                    for(l in attendance){
                        viewModel.deleteEventAttendance(l)
                    }
                    for(l in dishes){
                        viewModel.deleteEventDish(l)
                    }
                }

                binding.edit.setOnClickListener{
                    val action = EventListFragmentDirections.actionListFragmentToAddFragment(list[position])
                    holder.itemView.findNavController().navigate(action)
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(events: List<Event>){
        this.list = events
        notifyDataSetChanged()
    }
    inner class MyViewHolder(val binding: EventCustomRowBinding): RecyclerView.ViewHolder(binding.root)

}