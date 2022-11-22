package com.example.splitthebill.view


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.splitthebill.adapter.PersonAdapter
import com.example.splitthebill.databinding.ActivitySplitBinding
import com.example.splitthebill.model.Constant.PERSONS
import com.example.splitthebill.model.Person
import java.util.ArrayList

class SplitActivity : AppCompatActivity() {
    private val asb: ActivitySplitBinding by lazy {
        ActivitySplitBinding.inflate(layoutInflater)
    }

    private lateinit var personAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(asb.root)
        val personList = intent.getParcelableArrayListExtra<Person>(PERSONS)

        val personMutList: MutableList<Person>
        var sum = 0.0
        personList?.let { persons ->
            Log.v("teste", persons[0].toString())
            for (person in persons){
                sum += person.valorPago
            }
            val payPerPerson = (sum / persons.size)
            for (person in persons){
                person.valorPagar = payPerPerson - person.valorPago
            }
        }

        //personAdapter = PersonAdapter(this, )
        //asb.splitLv.adapter = personAdapter
    }
}