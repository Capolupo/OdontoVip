package br.com.pkodontovip.ui.novopaciente


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import br.com.pkodontovip.R
import br.com.pkodontovip.api.PacienteAPI
import br.com.pkodontovip.api.RetrofitClient
import br.com.pkodontovip.model.Global
import br.com.pkodontovip.model.Paciente
import kotlinx.android.synthetic.main.fragment_novo_paciente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileNotFoundException


class NovoPacienteFragment : Fragment() {

    lateinit var thisContext : Context
    lateinit var imvcadastro : ImageView

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

        imvcadastro = view.findViewById(R.id.iv)
      //  var input :String = Global.pacienteAtual.urlImagem.toString()
      //  var bitmap : Bitmap = Global.decodeBase64(input)
     //   imvcadastro.setImageBitmap(bitmap)
        imvcadastro.setOnClickListener { v: View? ->  Global.OpenDialog(Global.activity)}


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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("Retorno", requestCode.toString() + " " + resultCode.toString())
        if (resultCode == Activity.RESULT_OK && requestCode == Global.RESULT_LOAD_IMG) {
            try {
                val imageUri = data!!.getData()
                val selectedImage = Global.decodeUri(imageUri, 150, Global.activity)
                val base64 = Global.encodeToBase64(selectedImage, Bitmap.CompressFormat.JPEG)
                imvcadastro.setImageBitmap(Global.decodeBase64(base64))
                //Global.pacienteAtual.urlImagem = base64
                System.gc()
                imvcadastro.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(thisContext, "Erro ao pegar a foto.", Toast.LENGTH_LONG).show()
            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == Global.REQUEST_IMAGE_CAPTURE) {
            val extras = data!!.getExtras()
            val imageBitmap = extras!!.get("data") as Bitmap
            imvcadastro.setImageBitmap(imageBitmap)
            val base64 = Global.encodeToBase64(imageBitmap, Bitmap.CompressFormat.JPEG)
            //Global.pacienteAtual.urlImagem = base64
            System.gc()
            imvcadastro.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(thisContext, "Você não selecionou uma foto.", Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun limparCampos(){
        inputMarca.editText?.setText("")
        inputModelo.editText?.setText("")
        inputAno.editText?.setText("")
        inputPlaca.editText?.setText("")
    }
}// Required empty public constructor
