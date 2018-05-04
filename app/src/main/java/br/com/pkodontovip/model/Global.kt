package br.com.pkodontovip.model

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat.startActivity
import br.com.pkodontovip.ui.main.MainActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.graphics.BitmapFactory
import android.app.Activity
import android.graphics.Bitmap
import android.content.DialogInterface.BUTTON_POSITIVE
import android.content.DialogInterface.BUTTON_NEGATIVE
import android.content.DialogInterface
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.os.Environment.getExternalStorageDirectory
import android.support.v7.app.AlertDialog
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException


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
        lateinit var activity: Activity

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

        /**
         * ///Fotos64
         */
        //object fotosCode {
            var REQUEST_IMAGE_CAPTURE = 100
            var RESULT_LOAD_IMG = 200
        //}

        lateinit var imageToUploadUri: Uri
        fun OpenDialog(activity: Activity) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage("Deseja pegar a foto da Camera ou da Galeria?").setTitle("Pegue a foto")
            builder.setNegativeButton("Camera", DialogInterface.OnClickListener { dialogInterface, i ->
                val f = File(Environment.getExternalStorageDirectory(), "POST_IMAGE.jpg")
                imageToUploadUri = Uri.fromFile(f)
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
                    activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            })
            builder.setPositiveButton("Galeria", DialogInterface.OnClickListener { dialogInterface, i ->
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                activity.startActivityForResult(intent, RESULT_LOAD_IMG)
            })
            val alertDialog = builder.create()
            alertDialog.setOnShowListener(DialogInterface.OnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(0, 0, 0))
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(0, 0, 0))
            })
            alertDialog.show()
        }

        fun encodeToBase64(image: Bitmap, compressFormat: Bitmap.CompressFormat): String {
            val byteArrayOS = ByteArrayOutputStream()
            image.compress(compressFormat, 20, byteArrayOS)
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
        }

        fun decodeBase64(input: String): Bitmap {
            val decodedBytes = Base64.decode(input, 0)
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        }

        @Throws(FileNotFoundException::class)
        fun decodeUri(uri: Uri, requiredSize: Int, activity: Activity): Bitmap {
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(activity.contentResolver.openInputStream(uri), null, o)

            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1

            while (true) {
                if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                    break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }

            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(activity.contentResolver.openInputStream(uri), null, o2)
        }
    }
}