package com.example.rp_2024

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.rp_2024.databaseStuff.PersonDatabase
import com.example.rp_2024.databinding.ActivityAddPersonBinding
import java.text.SimpleDateFormat
import java.util.Calendar

class AddPersonActivity : DrawerBaseActivity() {

    private lateinit var activityAddPersonBinding : ActivityAddPersonBinding
    private lateinit var editName : EditText
    private lateinit var editSurname : EditText
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    lateinit var isSwimmer : Switch
    private lateinit var save : Button
    private lateinit var text : TextView
    private lateinit var pickDate : Button

    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            PersonDatabase::class.java,
            "people.db"
        ).build()
    }

    private val viewModel by viewModels<PersonViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PersonViewModel(db.dao) as T
                }
            }
        }
    )

    @SuppressLint("MissingInflatedId", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddPersonBinding = ActivityAddPersonBinding.inflate(layoutInflater)

        setContentView(activityAddPersonBinding.root)
        allocateActivityTitle("add a Person")



        editName = findViewById(R.id.name)
        editSurname = findViewById(R.id.surname)
        isSwimmer = findViewById(R.id.isSwimmer)
        save = findViewById(R.id.save)
        text = findViewById(R.id.text)
        pickDate = findViewById(R.id.datePicker)

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)

            val myFormat = "yyyy/MM/dd"
            val sdf = SimpleDateFormat(myFormat)
            viewModel.onEvent(PersonEvent.SetBirthdate(sdf.format(myCalendar.time)))
        }

        pickDate.setOnClickListener(){
            DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        editName.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                viewModel.onEvent(PersonEvent.SetName(editName.text.toString()))
            }
        }
        editSurname.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus){
                viewModel.onEvent(PersonEvent.SetSurname(editSurname.text.toString()))
            }
        }
        isSwimmer.setOnClickListener{
            viewModel.onEvent(PersonEvent.SetSwimmer(isSwimmer.isChecked))
        }


        save.setOnClickListener{

            viewModel.onEvent(PersonEvent.SavePerson)

            val name = editName.text.toString()
            val surname = editSurname.text.toString()

            if(name.isBlank() || surname.isBlank()){
                text.text = "vyplnte vsechna pole"
            }



        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat)
        pickDate.setText(sdf.format(myCalendar.time))

    }


}
