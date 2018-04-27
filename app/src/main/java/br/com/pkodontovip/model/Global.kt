package br.com.pkodontovip.model

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Global {

    companion object {
        lateinit var database : FirebaseDatabase
        lateinit var clinicaRef : DatabaseReference
        lateinit var clinicaAtual : Paciente
        lateinit var mAuth : FirebaseAuth
        lateinit var listaDosPacientes : List<Paciente>

        var RC_SIGN_IN = 123
        var providers = Arrays.asList(
                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build())

        fun configurarFirebase(){
            database = FirebaseDatabase.getInstance("https://carproject-7daf0.firebaseio.com/")
            clinicaRef = database.getReference("Clinica")
        }
    }
}