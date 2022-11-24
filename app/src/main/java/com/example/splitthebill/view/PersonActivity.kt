package com.example.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.splitthebill.R
import com.example.splitthebill.databinding.ActivityPersonBinding
import com.example.splitthebill.model.Constant.EXTRA_PERSON
import com.example.splitthebill.model.Person
import kotlin.random.Random


class PersonActivity : AppCompatActivity() {
    private val apb: ActivityPersonBinding by lazy {
        ActivityPersonBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(apb.root)

        if (intent.getBooleanExtra("VIEW_PERSON", false)){
            apb.nameEt.isEnabled = false
            apb.valorPagoEt.isEnabled = false
            apb.descEt.isEnabled = false
            apb.saveBt.visibility = View.GONE
        }

        val receivedPerson = intent.getParcelableExtra<Person>(EXTRA_PERSON)
        receivedPerson?.let { person ->
            if (intent.getBooleanExtra("VIEW_PERSON", false)){
                apb.nameEt.setText(getString(R.string.name_view_person, person.name))
                apb.nameEt.isEnabled = false
                apb.valorPagoEt.setText(
                    getString(R.string.paid_value_view_person, "%.2f".format(person.valorPago.toString().toDouble()))
                )
                apb.descEt.setText(getString(R.string.desc_view_person, person.desc))
            }
            else {
                apb.nameEt.setText(person.name)
                apb.nameEt.isEnabled = false
                apb.valorPagoEt.setText(
                    "%.2f".format(person.valorPago.toString().toDouble()).replace(",", ".")
                )
                apb.descEt.setText(person.desc)
            }
        }

        apb.valorPagoEt.setOnFocusChangeListener { _, b ->
            with (apb.valorPagoEt){
                if (b) {
                    if (text.toString() == getString(R.string._0_00))
                        setText("")
                }
                else if (text.isEmpty()) {
                    setText(getString(R.string._0_00))
                }else {
                    if (text.toString().elementAt(text.length -1) == '.'){
                        text.removeRange(text.length -1,text.length -1)
                    }
                    setText("%.2f".format(text.toString().toDouble()).replace(",","."))
                }
            }
        }

        apb.saveBt.setOnClickListener {
            if (
                apb.nameEt.text.isNotEmpty() &&
                apb.valorPagoEt.text.isNotEmpty()
            ) {
                val person = Person(
                    id = receivedPerson?.id ?: Random(System.currentTimeMillis()).nextInt(),
                    name = apb.nameEt.text.toString(),
                    valorPago = apb.valorPagoEt.text.toString().toDouble(),
                    valorPagar = null,
                    valorReceber = null,
                    desc = apb.descEt.text.toString(),
                )
                setResult(
                    RESULT_OK,
                    Intent().putExtra(
                        EXTRA_PERSON,
                        person
                        )
                )
                finish()
            }else {
                Toast.makeText(
                    this, getString(R.string.valid_inputs_person_activity), Toast.LENGTH_LONG
                ).show()
            }
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