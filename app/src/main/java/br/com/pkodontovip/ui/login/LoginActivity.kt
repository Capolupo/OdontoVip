package br.com.pkodontovip.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.pkodontovip.R
import br.com.pkodontovip.ui.main.MainActivity
import android.util.Log
import android.widget.*
import br.com.pkodontovip.model.Paciente
import br.com.pkodontovip.model.Global
import com.google.firebase.database.*
import android.widget.Toast
import br.com.pkodontovip.model.Clinica


class LoginActivity : AppCompatActivity() {

    lateinit var ckManterConectado : CheckBox

    lateinit var photo : ImageView;
    lateinit var email : EditText;
    lateinit var senha : EditText;
    lateinit var entrar : Button;
    lateinit var cadastre : TextView;

    lateinit var myRef : DatabaseReference;
    lateinit var clinicaCar : Paciente;


    lateinit var context : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context = this
//        Global.mAuth = FirebaseAuth.getInstance();

        ckManterConectado = findViewById(R.id.ckManterConectado)

        photo = findViewById(R.id.login_imagem)
        email = findViewById(R.id.login_email)
        senha = findViewById(R.id.login_password)
        entrar = findViewById(R.id.login_entrar)
        cadastre = findViewById(R.id.login_cadastrar)
        cadastre.setOnClickListener { cadastrar() }

        entrar.setOnClickListener { entrar() }

    }

    fun loginFirebase(){
        if (email.text.toString().isNullOrEmpty() || senha.text.toString().isNullOrEmpty()){
            Toast.makeText(context, "Campo e-mail e senha não podem ser vazio.", Toast.LENGTH_LONG).show()
        }
        else{

//            Global.mAuth.signInWithEmailAndPassword(email.text.toString(), senha.text.toString())
//                    .addOnCompleteListener(this, OnCompleteListener<AuthResult> { task ->
//                        if (task.isSuccessful) {
//
//                            val pref = applicationContext.getSharedPreferences("MinhasPreferencias", MODE_PRIVATE)
//                            val editor = pref.edit()
//                            if ( ckManterConectado.isChecked ) {
//                                editor.putBoolean("ckManterConectado", true);
//                                Toast.makeText(this, "Você ira entrar direto nas próximas! ", Toast.LENGTH_LONG).show()
//                            }else{
//                                editor.putBoolean("ckManterConectado", false);
//                                Toast.makeText(this, "Para não entrar direto caça logoff! ", Toast.LENGTH_LONG).show()
//                            }
//                            editor.apply()
//
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("FragmentActivity.TAG", "signInWithEmail:success")
//                            val user = Global.mAuth.getCurrentUser()
//
//                            Global.pref.edit().putString(Global.emailSharedPreference, email.text.toString())
//
//                            startActivity(Intent(context, MainActivity::class.java))
//
//                            //updateUI(user)
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w("FragmentActivity.TAG", "signInWithEmail:failure", task.exception)
//                            Toast.makeText(context, task.exception?.localizedMessage,
//                                    Toast.LENGTH_SHORT).show()
//                            //updateUI(null)
//                        }
//
//                        // ...
//                    })

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == RC_SIGN_IN) {
//            val response = IdpResponse.fromResultIntent(data)
//
//            if (resultCode == Activity.RESULT_OK) {
//                // Successfully signed in
//                val user = FirebaseAuth.getInstance().getCurrentUser()
//
//                // ...
//            } else {
//                // Sign in failed, check response for error code
//                // ...
//            }
//        }
    }

    fun configurarFirebase(){
        Global.configurarFirebase()
        //conectarAoFireBase()
    }

    fun conectarAoFireBase(){
            Global.clinicaRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("DataSnap", dataSnapshot.toString())
                if (dataSnapshot.value.toString() != "null"){
                    val clinicaFire = dataSnapshot.child("Clinica").getValue(Paciente::class.java);
                    clinicaCar = clinicaFire ?: Paciente("0","",1,"","")
                }else {
//                    clinicaCar = Carro("0","marca","modelo",1,"placa","url",0)
//                    myRef.child("teste").setValue(clinicaCar)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("DataError", error.toString());
            }
        })
    }

    fun entrar(){
        Global.clinicaRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("DataSnap", dataSnapshot.toString())
                if (dataSnapshot.value.toString() != "null"){
                    val emailCoded : String = Global.emailToValidChar(email.text.toString())
                    if(dataSnapshot.hasChild(emailCoded)){
                        if(dataSnapshot.child(emailCoded).child("senha").getValue().toString().equals(senha.text.toString())) {
try {

                            val clinicaFire : Clinica
                            val dataSnapshotvar = dataSnapshot.child(emailCoded ?: "")

                            clinicaFire = dataSnapshotvar.getValue<Clinica>(Clinica::class.java)?: Clinica(0, "",  "", "")
                            Global.clinicaAtual = clinicaFire ?: Clinica(0, "",  "", "")

                            //Salva Preferencias de Manter conectado
                            val pref = applicationContext.getSharedPreferences("MinhasPreferencias", MODE_PRIVATE)
                            val editor = pref.edit()
                            if ( ckManterConectado.isChecked ) {
                                editor.putBoolean("ckManterConectado", true);
                                Toast.makeText(context, "Você ira entrar direto nas próximas! ", Toast.LENGTH_LONG).show()
                            }else{
                                editor.putBoolean("ckManterConectado", false);
                                Toast.makeText(context, "Para não entrar direto faça logoff! ", Toast.LENGTH_LONG).show()
                            }
                            editor.commit()

    startActivity(Intent(context, MainActivity::class.java))
    finish()
}catch (e:Exception){
    Log.e("Error", e.toString())
}
                        }else
                            Toast.makeText(context,
                                    "Senha não confere.",
                                    Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(context,
                                "Email não cadastrado.",
                                Toast.LENGTH_LONG).show()
                    }
                }else {
                    Toast.makeText(context, "e-mail não confere", Toast.LENGTH_LONG).show()
//                    clinicaCar = Carro("0","marca","modelo",1,"placa","url",0)
//                    myRef.child("teste").setValue(clinicaCar)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("DataError", error.toString());
            }
        })
    }

    fun cadastrar(){
        startActivity(Intent(this, SignUp::class.java))
    }
}