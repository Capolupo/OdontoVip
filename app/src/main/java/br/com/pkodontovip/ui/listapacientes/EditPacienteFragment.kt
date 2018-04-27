package br.com.pkodontovip.ui.listapacientes

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.pkodontovip.R

class EditPacienteFragment : Fragment() {

    lateinit var thisContext : Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        thisContext = inflater.context
        return inflater.inflate(R.layout.fragment_edit_paciente, container, false)
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}