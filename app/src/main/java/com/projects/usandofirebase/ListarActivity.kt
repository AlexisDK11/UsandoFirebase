package com.projects.usandofirebase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.projects.usandofirebase.adapter.MeuAdapter
import com.projects.usandofirebase.database.DatabaseHandler
import com.projects.usandofirebase.databinding.ActivityListarBinding

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
        val registros = banco.cursorList()

        val adapter = MeuAdapter(this, registros)
        binding.lvPrincipal.adapter = adapter
    }
}