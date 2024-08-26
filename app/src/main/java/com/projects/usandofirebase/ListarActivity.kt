package com.projects.usandofirebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.projects.usandofirebase.adapter.MeuAdapter
import com.projects.usandofirebase.database.DatabaseHandler
import com.projects.usandofirebase.databinding.ActivityListarBinding
import com.projects.usandofirebase.entity.Cadastro

class ListarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityListarBinding
    private lateinit var banco : DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DatabaseHandler( this )

        binding.btIncluir.setOnClickListener{
            btIncluirOnClick()
        }
    }

    private fun btIncluirOnClick() {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }

    override fun onStart() {
        super.onStart()

        val banco = Firebase.firestore

        banco.collection("cadastro")
            .get()
            .addOnSuccessListener {result ->
                var registros = mutableListOf<Cadastro>()
                for(document in result){
                    val cadastro = Cadastro(
                        document.data.get("_id").toString().toInt(),
                        document.data.get("nome").toString(),
                        document.data.get("telefone").toString()
                    )
                }

                val adapter = MeuAdapter(this, registros)
                binding.lvPrincipal.adapter = adapter
                println("Sucesso")
            }.addOnFailureListener { e->
                println("Erro: ${e.message}")
            }


    }
}