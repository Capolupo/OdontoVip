package br.com.pkodontovip.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import br.com.pkodontovip.R
import br.com.pkodontovip.ui.login.LoginActivity
import br.com.pkodontovip.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import android.R.id.edit
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import br.com.pkodontovip.model.Global
import br.com.pkodontovip.model.Global.Companion.pref


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Global.configurarFirebase()
        Global.pref = applicationContext.getSharedPreferences("MinhasPreferencias", MODE_PRIVATE)
        carregar()
    }

    fun carregar() {
        val animacao = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splash)
        ivLogoSplash.startAnimation(animacao)


        val ckchecado = pref.getBoolean("ckManterConectado", false)

        Log.i("manter conectado", ckchecado.toString())

        if ( ckchecado == true ) {
            Toast.makeText(this, "Você ira entrar direto! Para novologgin faça logoff", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }else {
            Handler().postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
            }, 3000)
        }
    }
}
