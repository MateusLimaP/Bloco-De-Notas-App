package com.mateuslima.blocodenotas.feature_notas.presentation.util

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import com.mateuslima.blocodenotas.R

object NotasDialog {

    fun permissaoEscritaGaleria(context: Context, writeLauncher: ActivityResultLauncher<String>){
        val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setTitle("Permissão Escrita")
            .setMessage("Para acessar a galeria e selecionar uma imagem é necessário aceitar a permissao de escrita no app")
            .setPositiveButton("Confirmar"){dialog,_ ->

                writeLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            .setNegativeButton("Cancelar"){dialog,_ -> }
        dialog.show()
    }
}