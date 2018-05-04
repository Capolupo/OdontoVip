package br.com.pkodontovip.ui.listapacientes

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import br.com.pkodontovip.R
import br.com.pkodontovip.api.PacienteAPI
import br.com.pkodontovip.api.RetrofitClient
import br.com.pkodontovip.model.Global
import br.com.pkodontovip.model.Paciente
import br.com.pkodontovip.ui.main.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_edit_paciente.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileNotFoundException

class EditPacienteFragment : android.support.v4.app.Fragment() {

    lateinit var thisContext : Context
    lateinit var edit_iv : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        thisContext = inflater.context
        return inflater.inflate(R.layout.fragment_edit_paciente, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_iv = view.findViewById(R.id.edit_iv)
        if(Global.pacienteAtual.urlImagem.isNullOrEmpty()){
            edit_iv.setImageDrawable(
                    ContextCompat.getDrawable(thisContext,R.drawable.cancel)
            )
        }
        else{
            if(Global.pacienteAtual.urlImagem!!.contains("http"))
                Picasso.get()
                        .load(Global.pacienteAtual.urlImagem)
                        .placeholder(R.drawable.refresh)
                        .error(R.drawable.cancel)
                        .into(edit_iv);
            else{
                var input :String = Global.pacienteAtual.urlImagem.toString()
                var bitmap : Bitmap = Global.decodeBase64(input)
                edit_iv.setImageBitmap(bitmap)
            }
        }
        edit_iv.setOnClickListener { v: View? ->  Global.OpenDialog(Global.activity)}

        if (!Global.pacienteAtual.nome.isNullOrEmpty())
            edit_nome.setText(Global.pacienteAtual.nome)
        if (!Global.pacienteAtual.idade.toString().isNullOrEmpty())
            edit_idade.setText(Global.pacienteAtual.idade.toString())
        if (!Global.pacienteAtual.descricao.isNullOrEmpty())
            edit_descricao.setText(Global.pacienteAtual.descricao)

        edit_salvar.setOnClickListener { v: View ->
        if (camposVazil()) {
            Toast.makeText(thisContext, "Preencha todos os campos", Toast.LENGTH_LONG).show()
        } else {
            val api = RetrofitClient
                    .getInstance()
                    .create(PacienteAPI::class.java)
            val paciente = Paciente(Global.pacienteAtual.id,
                    edit_nome?.text.toString(),
                    edit_idade?.text.toString().toInt(),
                    edit_descricao?.text.toString(),
                    Global.pacienteAtual.urlImagem)
            api.salvar(paciente)
                    .enqueue(object : Callback<Void> {
                        override fun onFailure(call: Call<Void>?, t: Throwable?) {
                            Log.e("PACIENTE", t?.message)
                        }

                        override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                            Toast.makeText(thisContext, "Paciente " + edit_nome.text.toString() + " cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                            Global.fragmentManager.popBackStack()
                        }
                    })
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
                edit_iv.setImageBitmap(Global.decodeBase64(base64))
                Global.pacienteAtual.urlImagem = base64
                System.gc()
                edit_iv.setImageBitmap(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
                Toast.makeText(thisContext, "Erro ao pegar a foto.", Toast.LENGTH_LONG).show()
            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == Global.REQUEST_IMAGE_CAPTURE) {
            val extras = data!!.getExtras()
            val imageBitmap = extras!!.get("data") as Bitmap
            edit_iv.setImageBitmap(imageBitmap)
            val base64 = Global.encodeToBase64(imageBitmap, Bitmap.CompressFormat.JPEG)
            Global.pacienteAtual.urlImagem = base64
            System.gc()
            edit_iv.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(thisContext, "Você não selecionou uma foto.", Toast.LENGTH_LONG).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun camposVazil():Boolean{
        if(edit_nome.text.isNullOrEmpty()||
                edit_idade.text.isNullOrEmpty()||
                edit_descricao.text.isNullOrEmpty())
            return true
        else
            return false
    }
}