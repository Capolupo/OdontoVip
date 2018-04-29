package br.com.pkodontovip.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import br.com.pkodontovip.R
import br.com.pkodontovip.model.Global
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import android.R.id.edit
import android.content.SharedPreferences
import android.widget.CheckBox
import br.com.pkodontovip.model.Clinica
import br.com.pkodontovip.model.Paciente


class SignUp : AppCompatActivity() {

    lateinit var email : EditText
    lateinit var senha : EditText
    lateinit var confSenha : EditText
    lateinit var cadastrar : Button
    lateinit var ckManterConectado : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_siginup)

        email = findViewById(R.id.signup_email)
        senha = findViewById(R.id.signup_senha)
        confSenha = findViewById(R.id.signup_confsenha)
        cadastrar = findViewById(R.id.signup_cadastrar)


        cadastrar.setOnClickListener{ cadastrarClinica() }
    }
    fun camposVazil():Boolean{
        if(email.text.isNullOrEmpty()||
                senha.text.isNullOrEmpty()||
                confSenha.text.isNullOrEmpty())
            return true
        else
            return false
    }
    fun cadastrarClinica(){
        if (camposVazil())
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
        else {
            try {
                    val clinica = Clinica(0, email.text.toString(), senha.text.toString(), "")

                    Global.clinicaRef.child(email.text.toString().replace(".","2e")).setValue(clinica)

                }catch (e: Exception) {

                    Log.e("ErroFirebase", e.toString())

                }





//            Global.mAuth.createUserWithEmailAndPassword(
//                    email.text.toString(),
//                    senha.text.toString())
//                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
//                        if (task.isSuccessful) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("FragmentActivity.TAG", "createUserWithEmail:success")
//                            val user = Global.mAuth.currentUser
//                            finish()
//                            //updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("FragmentActivity.TAG", "createUserWithEmail:failure", task.exception)
//                            Toast.makeText(this, "A senha precisa ter 6 ou mais caracteres",
//                                    Toast.LENGTH_SHORT).show()
//                            //updateUI(null)
//                        }
//                    })
        }
    }
}