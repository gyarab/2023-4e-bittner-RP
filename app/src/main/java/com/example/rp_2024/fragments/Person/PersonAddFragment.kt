package com.example.rp_2024.fragments.Person

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.rp_2024.R
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databinding.FragmentPersonAddBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat


class PersonAddFragment : Fragment() {


    private var addBinding: FragmentPersonAddBinding? = null

    private lateinit var viewModel: MyViewModel
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPersonAddBinding.inflate(inflater, container, false)
        addBinding = binding

        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val spinner = binding.status
        val items = arrayOf("dítě", "instruktor", "vedoucí", "kmeňák")
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.adapter = adapter

        var editedPersonId = -1

        if(arguments != null){
            if(PersonAddFragmentArgs.fromBundle(requireArguments()).currentPerson != null){
                editedPersonId = writePersonInfo(binding)
            }
        }

        viewModel.upsertPerson(Person(20, "alois", "jirásek", 0, "", 	323087057, -1, -1, "", "", "staré pověsti"))
        viewModel.upsertPerson(Person(21, "george", "orwell", 3, "", 		454584257, -1, -1, "", "541616158", ""))
        viewModel.upsertPerson(Person(22, "karel", "mácha", 2, "", 			959505857, -1, -1, "macha@com", "546496158", ""))
        viewModel.upsertPerson(Person(23, "božena", "němcová", 1, "", 			-618413743, -1, 24, "nemcova@bozena", "543185158", "babička"))
        viewModel.upsertPerson(Person(24, "Jana", "němcová", 4, "", 			-618413743, -1, -1, "jana@mail", "557465158", "babička"))


        binding.button.setOnClickListener{

                upsertData(binding, editedPersonId)

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }



        return binding.root
    }

    private fun writePersonInfo(binding: FragmentPersonAddBinding): Int {

            val p: Person = arguments.let { PersonAddFragmentArgs.fromBundle(it!!).currentPerson!! }

            binding.name.setText(p.name)
            binding.surname.setText(p.surname)
            binding.alias.setText(p.alias)
            binding.status.prompt = when (p.status) {
                0 -> "dítě"
                1 -> "instruktor"
                2 -> "vedoucí"
                3 -> "kmeňák"
                else -> "dítě"
            }

            val sdf = java.text.SimpleDateFormat("yyyyMMdd")
            val date = java.util.Date(p.birthdate?.times(1000) ?: (1094853600 * 1000))
            val s = sdf.format(date)
            binding.dayPicker.value = s.subSequence(6, 8).toString().toInt()
            binding.monthPicker.value = s.subSequence(4, 6).toString().toInt()
            binding.monthPicker.value = s.subSequence(0, 4).toString().toInt()

            binding.email.setText(p.email)
            binding.phone.setText(p.phoneNumber)
            binding.note.setText(p.note)

            if (p.motherId != null && p.motherId != -1) {
                val m = viewModel.getPerson(p.motherId!!)
                binding.motherName.setText(m.name)
                binding.motherSurname.setText(m.surname)
                binding.motherEmail.setText(m.email)
                binding.motherPhone.setText(m.phoneNumber)
            }
            if (p.fatherId != null && p.fatherId != -1) {
                val f = viewModel.getPerson(p.fatherId!!)
                binding.fatherName.setText(f.name)
                binding.fatherSurname.setText(f.surname)
                binding.fatherEmail.setText(f.email)
                binding.fatherPhone.setText(f.phoneNumber)
            }
            return p.id


    }

    @SuppressLint("SimpleDateFormat")
    private fun upsertData(binding: FragmentPersonAddBinding, editedPersonId: Int){
        val name = binding.name.text.toString().lowercase()
        val surname = binding.surname.text.toString().lowercase()
        val status = binding.status.selectedItem.toString()
        val alias = binding.alias.text.toString().lowercase()
        var day = binding.dayPicker.value.toString()
        var month = binding.monthPicker.value.toString()
        val year = binding.yearPicker.value.toString()
        val email = binding.email.text.toString()
        val phone = binding.phone.text.toString()
        val note = binding.note.text.toString()
        val mName = binding.motherName.text.toString().lowercase()
        val mSurname = binding.motherSurname.text.toString().lowercase()
        val mEmail = binding.motherEmail.text.toString()
        val mPhone = binding.motherPhone.text.toString()
        val fName = binding.fatherName.text.toString().lowercase()
        val fSurname = binding.fatherSurname.text.toString().lowercase()
        val fEmail = binding.fatherEmail.text.toString()
        val fPhone = binding.fatherPhone.text.toString()

        if(day.length == 1) {
            day = "0$day"
        }
        if(month.length == 1) {
            month = "0$month"
        }
        val birthdate = day + month + year
        var date = SimpleDateFormat("ddMMyyyy").parse(birthdate)
        if(date == null){
            date = SimpleDateFormat("ddMMyyyy").parse("11092004")
        }
        val unixTime = date.time

        var stat = 0
        when (status){
            "dítě" -> stat = 0
            "instruktor" -> stat = 1
            "vedoucí" -> stat = 2
            "kmeňák" -> stat = 3
        }

        val person = Person(0, name, surname, stat, alias, unixTime, -1, -1, email, phone, note)

        //uloz matku a otce
        runBlocking {
            val mother = launch {
                if (mName.isEmpty() && mSurname.isEmpty()) {
                } else {
                    val m = viewModel.getByNameAndSurname(mName, mSurname)
                    val mother = Person(0, mName, mSurname, 4, "", 1094853600, -1, -1, mEmail, mPhone, "")
                    if (m.isEmpty()) {
                        viewModel.upsertPerson(mother)
                    } else {
                        val id = m[0].id
                        mother.id = id
                        viewModel.upsertPerson(mother)
                    }
                    delay(250)
                    person.motherId = viewModel.getByNameAndSurname(mName, mSurname)[0].id
                }
            }
            val father = launch {
                if (fName.isEmpty() && fSurname.isEmpty()) {
                } else {
                    val f = viewModel.getByNameAndSurname(fName, fSurname)
                    val father = Person(0, fName, fSurname, 4, "", 1094853600, -1, -1, fEmail, fPhone, "")
                    if (f.isEmpty()) {
                        viewModel.upsertPerson(father)
                    } else {
                        val id = f[0].id
                        father.id = id
                        viewModel.upsertPerson(father)
                    }
                    delay(250)
                    person.fatherId = viewModel.getByNameAndSurname(fName, fSurname)[0].id
                }
            }
            mother.join()
            father.join()
            if(editedPersonId != -1){
                person.id = editedPersonId
                viewModel.upsertPerson(person)
            } else {
                val p = viewModel.getByNameAndSurname(name, surname)
                if (p.isEmpty()) {
                    viewModel.upsertPerson(person)
                } else {
                    val id = p[0].id
                    person.id = id
                    viewModel.upsertPerson(person)
                }
            }
        }
        if(inputCheck(name, surname)){
            Toast.makeText(requireContext(), "člen přidán", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(requireContext(), "POZOR jméno, nebo příjmení není vyplněné", Toast.LENGTH_LONG).show()
            Toast.makeText(requireContext(), "člen byl přidán", Toast.LENGTH_LONG).show()
        }


    }

    private fun inputCheck(name: String, surname: String): Boolean{
        return !(TextUtils.isEmpty(name) || TextUtils.isEmpty(surname))
    }

}