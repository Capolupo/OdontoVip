package br.com.pkodontovip.model


data class Paciente(var id : String?,
                    var nome      : String,
                    var idade     : Int,
                    var descricao : String?,
                    var urlImagem : String?
)