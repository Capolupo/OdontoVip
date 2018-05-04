package br.com.pkodontovip.api


import br.com.pkodontovip.model.Paciente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

/**
 * Created by Andre on 25/03/2018.
 */
interface PacienteAPI {
    @GET("/paciente")
    fun buscarTodos() : Call<List<Paciente>>

    @POST("/paciente")
    fun salvar(@Body paciente: Paciente):Call<Void>

    @HTTP(method = "DELETE", path = "/paciente", hasBody = true)
    fun excluir(@Body paciente : Paciente) : Call<Void>
}