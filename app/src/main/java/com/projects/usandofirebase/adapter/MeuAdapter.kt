package com.projects.usandofirebase.adapter
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import com.projects.usandofirebase.MainActivity
import com.projects.usandofirebase.R
import com.projects.usandofirebase.entity.Cadastro


class MeuAdapter(val context : Context, val registros : MutableList<Cadastro>) : BaseAdapter() {

    override fun getCount(): Int {
        return registros.size
    }

    override fun getItem(position: Int): Any {

        val cadastro = registros.get( position )
        return cadastro
    }

    override fun getItemId(position: Int): Long {
        val cadastro = registros.get( position )
        return cadastro._id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater
        val v = inflater.inflate( R.layout.elemento_lista, null )

        val tvNomeElementoLista = v.findViewById<TextView>( R.id.tvNomeElementoLista )
        val tvTelefoneElementoLista = v.findViewById<TextView>( R.id.tvTelefoneElementoLista )
        val btEditarElementoLista = v.findViewById<ImageButton>( R.id.btEditarElementoLista )

        val cadastro = registros.get( position )

        tvNomeElementoLista.setText( cadastro.nome )
        tvTelefoneElementoLista.setText( cadastro.telefone )

        btEditarElementoLista.setOnClickListener{
            val cadastro = registros.get( position )

            val intent = Intent( context, MainActivity::class.java)

            intent.putExtra( "cod", cadastro._id )
            intent.putExtra( "nome", cadastro.nome )
            intent.putExtra( "telefone", cadastro.telefone )

            context.startActivity( intent )
        }

        return v
    }
}