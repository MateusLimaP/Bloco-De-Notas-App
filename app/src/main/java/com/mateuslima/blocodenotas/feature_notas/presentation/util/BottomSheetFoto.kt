package com.mateuslima.blocodenotas.feature_notas.presentation.util

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.databinding.BottomSheetFotoBinding

class BottomSheetFoto (
    private val context: Context,
    private val listener: BottomSheetFotoListener
) {
    private var _binding: BottomSheetFotoBinding? = null
    private val binding get() = _binding!!


    init {
         val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_foto, null)
        _binding = BottomSheetFotoBinding.bind(view)
    }


    fun show(){
        val dialog = BottomSheetDialog(context)
        dialog.setContentView(binding.root)

        binding.textCamera.setOnClickListener {
            listener.cameraSelecionada()
            dialog.dismiss()
        }
        binding.textGaleria.setOnClickListener {
            listener.galeriaSelecionada()
            dialog.dismiss()
        }
        binding.textInternet.setOnClickListener {
            listener.internetSelecionada()
            dialog.dismiss()
        }

        dialog.show()

        dialog.setOnCancelListener { _binding = null }
        dialog.setOnDismissListener { _binding = null }

    }


    interface BottomSheetFotoListener{
        fun cameraSelecionada()
        fun galeriaSelecionada()
        fun internetSelecionada()
    }

}