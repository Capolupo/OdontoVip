package br.com.pkodontovip.ui.novopaciente


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import br.com.pkodontovip.R
import br.com.pkodontovip.api.PacienteAPI
import br.com.pkodontovip.api.RetrofitClient
import br.com.pkodontovip.model.Paciente
import kotlinx.android.synthetic.main.fragment_novo_paciente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NovoPacienteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_novo_paciente, container, false)    }

    fun camposVazil():Boolean{
        if(inputMarca.editText?.text.isNullOrEmpty()||
        inputModelo.editText?.text.isNullOrEmpty()||
        inputAno.editText?.text.isNullOrEmpty()||
        inputPlaca.editText?.text.isNullOrEmpty())
            return true
        else
            return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSalvar.setOnClickListener {
            if (!camposVazil()) {
                val api = RetrofitClient
                        .getInstance()
                        .create(PacienteAPI::class.java)
                val paciente = Paciente(null,
                        inputModelo.editText?.text.toString(),
                        inputMarca.editText?.text.toString().toInt(),
                        inputAno.editText?.text.toString(),
                        "")
                api.salvar(paciente)
                        .enqueue(object : Callback<Void> {
                            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                                Log.e("PACIENTE", t?.message)
                            }

                            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                                Toast.makeText(context, "Paciente " + inputModelo.editText?.text.toString() + " cadastrado com sucesso.", Toast.LENGTH_SHORT).show()
                            }
                        })
            }
            else{
                Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun limparCampos(){
        inputMarca.editText?.setText("")
        inputModelo.editText?.setText("")
        inputAno.editText?.setText("")
        inputPlaca.editText?.setText("")
    }
}// Required empty public constructor
