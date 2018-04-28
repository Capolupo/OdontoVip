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
import android.widget.Toast


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        carregar()
    }

    fun carregar() {
        val animacao = AnimationUtils.loadAnimation(this,
                R.anim.animacao_splash)
        ivLogoSplash.startAnimation(animacao)


        val pref = applicationContext.getSharedPreferences("MinhasPreferencias", MODE_PRIVATE)
        val ckchecado = pref.getBoolean("ckManterConectado", false)

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
