package com.projects.usandofirebase.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.projects.usandofirebase.entity.Cadastro

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    val banco = Firebase.firestore

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS ${TABLE_NAME} (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, telefone TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "dbfile.sqlite"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "cadastro"
        private const val COD = 0
        private const val NOME = 1
        private const val TELEFONE = 2
    }

    fun insert(cadastro : Cadastro){
        val registro = hashMapOf(
            "_id" to cadastro._id,
            "nome" to cadastro.nome,
            "telefone" to cadastro.telefone
        )
        banco.collection("cadastro")
            .document(cadastro._id.toString())
            .set(registro)
            .addOnFailureListener {
                println("Sucesso")
            }.addOnFailureListener { e->
                println("Erro: ${e.message}")
            }

        //db.insert(TABLE_NAME, null, registro)
    }
    fun update(cadastro : Cadastro){
        val registro = hashMapOf(
            "_id" to cadastro._id,
            "nome" to cadastro.nome,
            "telefone" to cadastro.telefone
        )
        banco.collection("cadastro")
            .document(cadastro._id.toString())
            .set(registro)
            .addOnFailureListener {
                println("Sucesso")
            }.addOnFailureListener { e->
                println("Erro: ${e.message}")
            }
    }
    fun delete(id : Int){
        banco.collection("cadastro")
            .document(id.toString())
            .delete()
            .addOnSuccessListener {
                println("Sucesso")
            }
            .addOnFailureListener { e->
                println("Erro: ${e.message}")
            }
    }
    fun find(id : Int ) : Cadastro?{
        val db = this.writableDatabase

        val registro = db.query(
            TABLE_NAME,
            null,
            "_id=${id}",
            null,
            null,
            null,
            null
        )

        if (registro.moveToNext()){
            val cadastro = Cadastro(
                id,
                registro.getString(NOME),
                registro.getString(TELEFONE)
            )
            return  cadastro
        }else{
            return null
        }
    }
    fun list() : MutableList<Cadastro>{

        var registros = mutableListOf<Cadastro>()

        banco.collection("cadastro")
            .get()
            .addOnSuccessListener {result ->
                for(document in result){
                    val cadastro = Cadastro(
                        document.data.get("_id").toString().toInt(),
                        document.data.get("nome").toString(),
                        document.data.get("telefone").toString()
                    )
                }
                println("Sucesso")
            }.addOnFailureListener { e->
                println("Erro: ${e.message}")
            }

        return registros
    }
    fun cursorList() : Cursor {
        val db = this.writableDatabase

        val registro = db.query(
            "cadastro",
            null,
            null,
            null,
            null,
            null,
            null
        )

        return registro
    }
}