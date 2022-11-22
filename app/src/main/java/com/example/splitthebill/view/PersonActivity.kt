package com.example.splitthebill.view

import android.content.Intent
import android.os.Bundle
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
            apb.saveBt.text = getString(R.string.close)
        }

        val receivedPerson = intent.getParcelableExtra<Person>(EXTRA_PERSON)
        receivedPerson?.let { person ->
            apb.nameEt.setText(person.name)
            apb.valorPagoEt.setText(person.valorPago.toString())
            apb.descEt.setText(person.desc)
        }

        apb.valorPagoEt.setOnFocusChangeListener { _, b ->
            if (b){
                if (apb.valorPagoEt.text.toString() == getString(R.string._0_00))
                    apb.valorPagoEt.setText("")
            }
            else{
                apb.valorPagoEt.text.ifEmpty {
                    apb.valorPagoEt.setText(getString(R.string._0_00))
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
}