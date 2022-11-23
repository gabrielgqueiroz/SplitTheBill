package com.example.splitthebill.view


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.splitthebill.R
import com.example.splitthebill.adapter.PersonAdapter
import com.example.splitthebill.databinding.ActivitySplitBinding
import com.example.splitthebill.model.Constant.PERSONS
import com.example.splitthebill.model.Person

class SplitActivity : AppCompatActivity() {
    private val asb: ActivitySplitBinding by lazy {
        ActivitySplitBinding.inflate(layoutInflater)
    }

    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asb.root)
        val personList = intent.getParcelableArrayListExtra<Person>(PERSONS)

        var sum = 0.0
        personList?.let { persons ->
            for (person in persons){
                sum += person.valorPago
            }
            val payPerPerson = (sum / persons.size)

            for (person in persons){
                val payOrReceive = payPerPerson - person.valorPago
                person.valorPagar = if ((payOrReceive) > 0) payOrReceive else 0.0
                person.valorReceber = if ((payOrReceive) < 0) -payOrReceive else 0.0
            }
            personAdapter = PersonAdapter(this, personList.toMutableList(), true)
            asb.splitLv.adapter = personAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_secondary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.closeMi -> {
                finish()
                true
            }
            else -> { false }
        }
    }
}