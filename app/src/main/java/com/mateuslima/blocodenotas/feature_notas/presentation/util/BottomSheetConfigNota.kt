package com.mateuslima.blocodenotas.feature_notas.presentation.util

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.databinding.BottomSheetConfigNotaBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.CoresAdapter

class BottomSheetConfigNota(
    private val context: Context,
    private val nota: Nota,
    private val listener: BottomSheetConfigNotaListener
) : CoresAdapter.CoresAdapterListener {

    private var _binding: BottomSheetConfigNotaBinding? = null
    private val binding get() = _binding!!

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_config_nota, null)
        _binding = BottomSheetConfigNotaBinding.bind(view)
    }

    fun show(){
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(binding.root)

        val corAdapter = CoresAdapter(this)
        binding.recyclerCor.apply {
            layoutManager = LinearLayoutManager(context).apply { orientation = LinearLayoutManager.HORIZONTAL }
            adapter = corAdapter
            setHasFixedSize(true)
        }

        binding.apply {
            textCompartilhar.setOnClickListener {
                listener.compartilharNota(nota)
                dialog.dismiss()
            }
            textCopiar.setOnClickListener {
                listener.copiarNota(nota)
                dialog.dismiss()
            }
            textDeletar.setOnClickListener {
                listener.deletarNota(nota)
                dialog.dismiss()
            }
        }

        dialog.show()
        // remover referencias
        dialog.setOnDismissListener { _binding = null }
        dialog.setOnCancelListener { _binding = null }

    }

    override fun onClickCorSelecionada(corHex: String) {
        listener.atualizarCor(corHex, nota)
    }

    interface BottomSheetConfigNotaListener{
        fun atualizarCor(corHex: String, nota: Nota)
        fun compartilharNota(nota: Nota)
        fun deletarNota(nota: Nota)
        fun copiarNota(nota: Nota)

    }
}