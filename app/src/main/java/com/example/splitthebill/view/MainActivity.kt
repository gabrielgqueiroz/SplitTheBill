package com.example.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.splitthebill.R
import com.example.splitthebill.adapter.PersonAdapter
import com.example.splitthebill.databinding.ActivityMainBinding
import com.example.splitthebill.model.Constant.EXTRA_PERSON
import com.example.splitthebill.model.Constant.PERSONS
import com.example.splitthebill.model.Constant.VIEW_PERSON
import com.example.splitthebill.model.Person
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val personList: MutableList<Person> = mutableListOf()

    // Adapter
    private lateinit var personAdapter: PersonAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        fillpersonList()
        personAdapter = PersonAdapter(this, personList,)

        amb.personsLv.adapter = personAdapter

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val person = result.data?.getParcelableExtra<Person>(EXTRA_PERSON)
                person?.let { _person->

                    if(personList.any { it.id == _person.id }){
                        val pos = personList.indexOfFirst { it.id == _person.id }
                        personList[pos] = _person

                    }
                    else {
                        personList.add(_person)
                    }
                    personAdapter.notifyDataSetChanged()
                }
            }
        }
        registerForContextMenu(amb.personsLv)

        amb.personsLv.onItemClickListener = object: AdapterView.OnItemClickListener {
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val person = personList[position]
                startActivity(
                    Intent(
                        this@MainActivity, PersonActivity::class.java
                    ).putExtra(
                        EXTRA_PERSON, person
                    ).putExtra(
                        VIEW_PERSON, true)
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addPersonMi -> {
                carl.launch(Intent(this, PersonActivity::class.java))
                true
            }
            R.id.calculateMi -> {
                startActivity(
                    Intent(
                        this@MainActivity, SplitActivity::class.java
                    ).putExtra(
                        PERSONS, ArrayList<Person> (personList)
                    )
                )
                true
            }
            else -> { false }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        return when(item.itemId){
            R.id.removePersonMi -> {
                personList.removeAt(position)
                personAdapter.notifyDataSetChanged()
                true
            }
            R.id.editPersonMi -> {
                val person = personList[position]
                carl.launch(
                    Intent(this, PersonActivity::class.java).putExtra(EXTRA_PERSON, person)
                )
                true
            }
            else -> { false }
        }
    }

    private fun fillpersonList() {
        for (i in 1..10) {
            personList.add(
                Person(
                    id = i,
                    name = "Nome $i",
                    valorPago = i.toDouble(),
                    valorPagar = null,
                    desc = "teste"
                )
            )
        }
    }
}