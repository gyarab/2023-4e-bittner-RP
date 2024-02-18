package com.example.rp_2024.people


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.rp_2024.DrawerBaseActivity
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databaseStuff.PersonDao
import com.example.rp_2024.databaseStuff.PersonDatabase
import com.example.rp_2024.databinding.ActivitySeePeopleBinding
import kotlinx.coroutines.flow.Flow
import com.example.rp_2024.R
import kotlinx.coroutines.flow.last


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySeePeopleBinding = ActivitySeePeopleBinding.inflate(layoutInflater)

        setContentView(activitySeePeopleBinding.root)
        allocateActivityTitle("Seznam členů")

        name = findViewById(R.id.name)
        surname = findViewById(R.id.surname)
        age = findViewById(R.id.age)
        edit = findViewById(R.id.doedit)

        var mainList: List<String?>
        var flowlist : Flow<List<Person>> = dao.getOrderedByName()


        val mainLayout = findViewById<View>(R.id.listLayout) as LinearLayout
        val li = applicationContext.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        for (p : Person in flowlist) {
            val tempView: View = li.inflate(R.layout.activity_see_people, null)
            val tempName = tempView.findViewById<View>(R.id.name) as TextView
            tempName.text = p.
            mainLayout.addView(tempView)
        }
    }



}


