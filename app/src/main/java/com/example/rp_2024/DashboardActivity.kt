package com.example.rp_2024

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.rp_2024.databaseStuff.Dish
import com.example.rp_2024.databaseStuff.Event
import com.example.rp_2024.databaseStuff.Ingredient
import com.example.rp_2024.databaseStuff.MyViewModel
import com.example.rp_2024.databaseStuff.Person
import com.example.rp_2024.databaseStuff.RecipeLine
import com.example.rp_2024.databinding.ActivityDashboardBinding

//prázdná aktivita
//dočasně slouží k nahrání testovacích dat do databáze
class DashboardActivity : DrawerBaseActivity() {

    private lateinit var activityDashboardBinding : ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(activityDashboardBinding.root)
        allocateActivityTitle("upload data")

        val viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        viewModel.upsertDish(Dish(20, "těstoviny s červenou", ""))
        viewModel.upsertRecipeLine(RecipeLine(21, 20, 21, 50, "g"))
        viewModel.upsertRecipeLine(RecipeLine(22, 20, 24, 1, "stroužek"))

        viewModel.upsertDish(Dish(21, "těstoviny s tuňákem", ""))
        viewModel.upsertRecipeLine(RecipeLine(23, 21, 21, 50, "g"))
        viewModel.upsertRecipeLine(RecipeLine(24, 21, 25, 20, "g"))

        viewModel.upsertDish(Dish(22, "česnečka", ""))
        viewModel.upsertRecipeLine(RecipeLine(25, 22, 24, 1, "g"))

        viewModel.upsertEvent(Event(20, "Velikonočka", 	1711614000, 	1711984920, 23, 22, "obráceně"))

        viewModel.upsertIngredient(Ingredient(20, "mouka", ""))
        viewModel.upsertIngredient(Ingredient(21, "těstoviny", ""))
        viewModel.upsertIngredient(Ingredient(22, "brambory", ""))
        viewModel.upsertIngredient(Ingredient(23, "pepř", ""))
        viewModel.upsertIngredient(Ingredient(24, "česnek", ""))
        viewModel.upsertIngredient(Ingredient(25, "porek", ""))
        viewModel.upsertIngredient(Ingredient(25, "tuňák", ""))

        viewModel.upsertPerson(Person(20, "alois", "jirásek", 0, "", 1269986400, -1, -1, "", "", "staré pověsti", false))
        viewModel.upsertPerson(Person(21, "george", "orwell", 0, "", 1238450400, -1, -1, "", "541616158", "", false))
        viewModel.upsertPerson(Person(22, "karel", "mácha", 1, "", 1175292000, -1, -1, "macha@com", "546496158", "", false))
        viewModel.upsertPerson(Person(23, "božena", "němcová", 2, "", 1080684000, -1, 24, "nemcova@bozena", "543185158", "babička", true))
        viewModel.upsertPerson(Person(24, "Jana", "němcová", 4, "", 828226800, -1, -1, "jana@mail", "557465158", "babička", false))

    }


}