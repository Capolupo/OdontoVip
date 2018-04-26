package br.com.pkodontovip.ui.listapacientes

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import br.com.pkodontovip.api.RetrofitClient
import br.com.pkodontovip.R
import br.com.pkodontovip.api.PacienteAPI
import br.com.pkodontovip.model.Paciente
import kotlinx.android.synthetic.main.erro.*
import kotlinx.android.synthetic.main.fragment_lista_pacientes.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaPacientesFragment : Fragment() {
    lateinit var loading : View
    lateinit var containerErro : View
    lateinit var tvMensagemErro : TextView
    lateinit var rvCarros : RecyclerView
    lateinit var thisContext : Context
    lateinit var progressBar : ProgressBar
    var progress : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        thisContext = inflater.context
        return inflater.inflate(R.layout.fragment_lista_pacientes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading = view.findViewById(R.id.loading)
        containerErro = view.findViewById(R.id.containerErro)
        tvMensagemErro = view.findViewById(R.id.tvMensagemErro)
        rvCarros = view.findViewById(R.id.rvCarros)
        progressBar = view.findViewById(R.id.loading_progressbar)

        setProgressValue(progress)
        carregarDados()
    }


    private fun setProgressValue(progress: Int) {

        // set the progress
        progressBar.setProgress(progress)
        // thread is used to change the progress value
        val thread = Thread(Runnable {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            setProgressValue(progress + 10)
        })
        thread.start()
    }

    fun carregarDados() {

        val api = RetrofitClient
                .getInstance()
                .create(PacienteAPI::class.java)

        loading.visibility = View.VISIBLE
        api.buscarTodos()
                .enqueue(object : Callback<List<Paciente>> {
                    override fun onFailure(call: Call<List<Paciente>>?,
                                           t: Throwable?) {
                        containerErro.visibility = View.VISIBLE
                        tvMensagemErro.text = t?.message
                        loading.visibility = View.GONE
                    }

                    override fun onResponse(call: Call<List<Paciente>>?,
                                            response: Response<List<Paciente>>?) {
                        containerErro.visibility = View.GONE
                        tvMensagemErro.text = ""

                        if(response?.isSuccessful == true) {
                            setupLista(response?.body())
                        } else {
                            containerErro.visibility = View.VISIBLE
                            tvMensagemErro.text = response?.errorBody()
                                    ?.charStream()?.readText()
                        }
                        loading.visibility = View.GONE
                    }

                })

    }
    fun setupLista(pacientes: List<Paciente>?) {
        pacientes.let {
            rvCarros.adapter = ListaPacientesAdapter(pacientes!!, thisContext)
            val layoutManager = LinearLayoutManager(context)
            rvCarros.layoutManager = layoutManager
        }

    }
}

