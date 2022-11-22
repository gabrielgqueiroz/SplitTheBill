package com.example.splitthebill.view

import android.app.Person
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.splitthebill.adapter.PersonAdapter
import com.example.splitthebill.databinding.ActivitySplitBinding
import com.example.splitthebill.model.Constant.PERSONS
import java.util.ArrayList

class SplitActivity : AppCompatActivity() {
    private val asb: ActivitySplitBinding by lazy {
        ActivitySplitBinding.inflate(layoutInflater)
    }

    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asb.root)
        val personList : ArrayList<Person> = intent.getParcelableArrayListExtra<Person>(PERSONS) as ArrayList<Person>
        val personMutList: MutableList<Person>
        val sum = 0
        personList?.let {
            Log.v("teste", it.toString())
            for (person in it) {
                Log.v("teste", person.toString())
            }
        }

        //personAdapter = PersonAdapter(this, )
        //asb.splitLv.adapter = personAdapter
    }
}