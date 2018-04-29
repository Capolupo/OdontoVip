package br.com.pkodontovip.model

import android.content.SharedPreferences
import com.google.android.gms.common.util.Hex
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.nio.charset.StandardCharsets
import java.util.*

class Global {

    companion object {
        lateinit var database : FirebaseDatabase
        lateinit var clinicaRef : DatabaseReference
        lateinit var pacienteAtual : Paciente
        lateinit var clinicaAtual : Clinica
//        lateinit var mAuth : FirebaseAuth
        lateinit var listaDosPacientes : List<Paciente>
        val loginSharedPreference: String = "LOGIN"
        val emailSharedPreference: String = "EMAIL"
        val senhaSharedPreference: String = "SENHA"
        lateinit var pref:SharedPreferences

        var RC_SIGN_IN = 123
//        var providers = Arrays.asList(
//                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
//                AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
//                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
//                AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
//                AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build())

        fun configurarFirebase(){
            database = FirebaseDatabase.getInstance("https://carproject-7daf0.firebaseio.com/")
            clinicaRef = database.getReference("Clinica")
        }
        fun emailToValidChar(string: String):String{
            val stringRetorno : String  = string.
                    replace(".","(2e)").
                    replace("#","(23)").
                    replace("$","(24)").
                    replace("[","(5b)").
                    replace("]","(5d)")
            return stringRetorno
        }
        fun validCharToEmail(string: String):String{
            val stringRetorno : String  = string.
                    replace("(2e)",".").
                    replace("(23)","#").
                    replace("(24)","$").
                    replace("(5b)","[").
                    replace("(5d)","]")
            return stringRetorno
        }
    }
}