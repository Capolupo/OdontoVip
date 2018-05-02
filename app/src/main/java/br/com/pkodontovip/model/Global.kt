package br.com.pkodontovip.model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat.startActivity
import br.com.pkodontovip.ui.main.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Global {

    companion object {
        lateinit var database : FirebaseDatabase
        lateinit var clinicaRef : DatabaseReference
        lateinit var pacienteAtual : Paciente
        lateinit var clinicaAtual : Clinica
        lateinit var listaDosPacientes : List<Paciente>
        val loginSharedPreference: String = "LOGIN"
        val emailSharedPreference: String = "EMAIL"
        val senhaSharedPreference: String = "SENHA"
        lateinit var pref:SharedPreferences
        lateinit var fragmentManager: FragmentManager

        fun configurarFirebase(){
            database = FirebaseDatabase.getInstance("https://odontovip-4bce7.firebaseio.com/")
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
    }
}