package br.com.pkodontovip.ui.main


import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import br.com.pkodontovip.R
import br.com.pkodontovip.ui.listapacientes.ListaPacientesFragment
import br.com.pkodontovip.ui.novopaciente.NovoPacienteFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sobre.*
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.LinearLayout
import br.com.pkodontovip.model.Global
import br.com.pkodontovip.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_edit_paciente.*
import java.io.FileNotFoundException


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lista -> {
                changeFragment(ListaPacientesFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_novo -> {
                changeFragment(NovoPacienteFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_sair -> {
                val alertDialog=AlertDialog.Builder(this)
                alertDialog.setMessage("Deseja sair do aplicativo? Desativa opção Manter Conectado!").
                        setPositiveButton("Sim", DialogInterface.OnClickListener{dialog: DialogInterface?, which: Int ->
                            val pref = applicationContext.getSharedPreferences("MinhasPreferencias", MODE_PRIVATE)
                            val editor = pref.edit()
                            editor.putBoolean("ckManterConectado", false);
                            editor.commit()
                            startActivity(Intent(this, LoginActivity::class.java))}).
                        setNegativeButton("Não",DialogInterface.OnClickListener{dialog: DialogInterface?, which: Int -> }).show()


                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun changeFragment(fragment: Fragment ){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerFragment, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.option_menu,menu)
        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("Retorno", requestCode.toString() + " " + resultCode.toString())
        if (resultCode == Activity.RESULT_OK && requestCode == Global.RESULT_LOAD_IMG) {
            try {
                val imageUri = data!!.getData()
                val selectedImage = Global.decodeUri(imageUri, 150, Global.activity)
                val base64 = Global.encodeToBase64(selectedImage, Bitmap.CompressFormat.JPEG)
                Global.pacienteAtual.urlImagem = base64
                System.gc()
                edit_iv.setImageBitmap(selectedImage)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Erro ao pegar a foto.", Toast.LENGTH_LONG).show()
            }

        } else if (resultCode == Activity.RESULT_OK && requestCode == Global.REQUEST_IMAGE_CAPTURE) {
            val extras = data!!.getExtras()
            val imageBitmap = extras!!.get("data") as Bitmap
            val base64 = Global.encodeToBase64(imageBitmap, Bitmap.CompressFormat.JPEG)
            Global.pacienteAtual.urlImagem = base64
            System.gc()
            edit_iv.setImageBitmap(imageBitmap)
        } else {
            Toast.makeText(this, "Você não selecionou uma foto.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        Global.fragmentManager = supportFragmentManager
        Global.activity = this
        changeFragment(ListaPacientesFragment())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var _view: View = getLayoutInflater().inflate(R.layout.activity_sobre, null)

        val sobre_link_facebook : LinearLayout = _view.findViewById(R.id.sobre_link_facebook)

        sobre_link_facebook.setOnClickListener{v: View? ->
            val url = "https://www.facebook.com/emimorigutiodontologia"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)}
        
        var alert = AlertDialog.Builder(this)
        alert.setView(_view)
        alert.create()
        alert.show()

//        val builder = AlertDialog.Builder(this@MainActivity)
//        val dialog: AlertDialog = builder.create()
//        dialog.layoutInflater.inflate(R.layout.activity_sobre,container)
//        dialog.show()
        return true
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        Global.backFuncition()
    }
}
