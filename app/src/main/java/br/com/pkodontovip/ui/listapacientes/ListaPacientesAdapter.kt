package br.com.pkodontovip.ui.listapacientes

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import br.com.pkodontovip.R
import br.com.pkodontovip.model.Global
import br.com.pkodontovip.model.Paciente
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.item_paciente.view.*
import kotlin.coroutines.experimental.coroutineContext

/**
 * Created by Andre on 25/04/2018.
 */
class ListaPacientesAdapter(private val pacientes:List<Paciente>,private val context:Context)
    :RecyclerView.Adapter<ListaPacientesAdapter.MeuViewHolder>(){

    override fun onBindViewHolder(holder: MeuViewHolder, position: Int) {
        val paciente = pacientes[position]
        holder?.let { it.bindView(paciente) }
    }

    override fun getItemCount(): Int {
        return pacientes.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeuViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.item_paciente, parent, false)
        return  MeuViewHolder(view)
    }

    class MeuViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindView(paciente:Paciente) {
            itemView.findViewById<TextView>(R.id.tvMarca).text = paciente.nome
            itemView.findViewById<TextView>(R.id.tvModelo).text = paciente.descricao
            if(paciente.urlImagem.isNullOrEmpty()){
                itemView.findViewById<ImageView>(R.id.ivFoto).setImageDrawable(
                        ContextCompat.getDrawable(itemView.context,R.drawable.erroufaustao)
                )
            }
            else{
                Picasso.get()
                        .load(paciente.urlImagem)
                        .placeholder(R.drawable.refresh)
                        .error(R.drawable.cancel)
                        .into(itemView.findViewById<ImageView>(R.id.ivFoto));
            }

            val buttonLongClickListener = { v: View ->

                true
            }
            itemView.findViewById<LinearLayout>(R.id.item_cliable).setOnLongClickListener(buttonLongClickListener)
        }
    }
}
