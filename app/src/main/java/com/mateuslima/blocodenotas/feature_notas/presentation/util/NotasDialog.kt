package com.mateuslima.blocodenotas.feature_notas.presentation.util

import android.Manifest
import android.content.Context
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AlertDialog
import com.mateuslima.blocodenotas.R

object NotasDialog {

    fun permissaoEscrita(context: Context, writeLauncher: ActivityResultLauncher<String>){
        val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setTitle(R.string.permissao_escrita_titulo)
            .setMessage(R.string.permissao_escrita_msg)
            .setPositiveButton(R.string.permissao_escrita_positivo){dialog,_ ->

                writeLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            .setNegativeButton(R.string.permissao_escrita_negativo){dialog,_ -> }
        dialog.show()
    }

    fun sairApp(context: Context, sair: (Boolean) -> Unit){
        val dialog = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            .setTitle(R.string.sairapp_titulo)
            .setMessage(R.string.sairapp_msg)
            .setPositiveButton(R.string.sairapp_positivo){_,_ -> sair.invoke(true)}
            .setNegativeButton(R.string.sairapp_negativo){_,_ -> sair.invoke(false)}
        dialog.show()
    }
}