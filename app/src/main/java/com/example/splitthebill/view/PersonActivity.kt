package com.example.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
            apb.saveBt.text = "Fechar"
        }

        val receivedPerson = intent.getParcelableExtra<Person>(EXTRA_PERSON)
        receivedPerson?.let { person ->
            apb.nameEt.setText(person.name)
            apb.valorPagoEt.setText(person.valorPago.toString())
            apb.descEt.setText(person.desc)
        }

        apb.saveBt.setOnClickListener {
            if (
                apb.nameEt.text.isNotEmpty() &&
                apb.valorPagoEt.text.isNotEmpty() &&
                apb.descEt.text.isNotEmpty()
            ) {
                val person = Person(
                    id = receivedPerson?.id ?: Random(System.currentTimeMillis()).nextInt(),
                    name = apb.nameEt.text.toString(),
                    valorPago = apb.valorPagoEt.text.toString().toDouble(),
                    valorPagar = null,
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
                    this, "Preencha os valores corretamente", Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}