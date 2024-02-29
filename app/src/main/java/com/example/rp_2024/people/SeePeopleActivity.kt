package com.example.rp_2024.people


import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.rp_2024.DrawerBaseActivity
import com.example.rp_2024.R
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databaseStuff.PersonDao
import com.example.rp_2024.databaseStuff.PersonDatabase
import com.example.rp_2024.databinding.ActivitySeePeopleBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period


class SeePeopleActivity : DrawerBaseActivity() {

    private lateinit var activitySeePeopleBinding : ActivitySeePeopleBinding
    private lateinit var name : TextView
    private lateinit var surname : TextView
    private lateinit var age : TextView
    private lateinit var edit : Button


    private val db by lazy{
        Room.databaseBuilder(
            applicationContext,
            PersonDatabase::class.java,
            "people.db"
        ).build()
    }
    private val dao : PersonDao = db.dao

    private val viewModel by viewModels<PeopleViewModel> (
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PeopleViewModel(db.dao) as T
                }
            }
        }
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySeePeopleBinding = ActivitySeePeopleBinding.inflate(layoutInflater)

        setContentView(activitySeePeopleBinding.root)
        allocateActivityTitle("Seznam členů")

        GlobalScope.launch {
            fillDatabase()
        }

        name = findViewById(R.id.name)
        surname = findViewById(R.id.surname)
        age = findViewById(R.id.age)
        edit = findViewById(R.id.doedit)

        var mainList: List<String?>
        var list : List<Person> = dao.getOrderedByName()


        val mainLayout = findViewById<View>(R.id.listLayout) as LinearLayout
        val li = applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (p : Person in list) {
            val tempView: View = li.inflate(R.layout.activity_see_people, null)
            val tempName = tempView.findViewById<View>(R.id.name) as TextView
            tempName.text = p.name
            val tempSurname = tempView.findViewById<View>(R.id.surname) as TextView
            tempSurname.text = p.surname
            val tempAge = tempView.findViewById<View>(R.id.age) as TextView
            // "yyyy/MM/dd" format data narozeni v databazi
            val date = p.birthdate
            tempAge.text = getAge(date.substring(0,3).toInt(), date.substring(5,6).toInt(), date.substring(8,9).toInt()).toString()
            mainLayout.addView(tempView)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(year: Int, month: Int, dayOfMonth: Int): Int {
        return Period.between(
            LocalDate.of(year, month, dayOfMonth),
            LocalDate.now()
        ).years
    }

    suspend fun fillDatabase(){
        val p1 = Person(
            name = "Pepa",
            surname = "Zdepa",
            birthdate = "2004/09/11",
            swimmer = true
        )
        val p2= Person(
            name = "Don",
            surname = "Juan",
            birthdate = "1985/12/04",
            swimmer = true
        )
        dao.upsertPerson(p1)
        dao.upsertPerson(p2)
    }
}


