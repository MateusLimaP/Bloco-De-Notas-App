package com.mateuslima.blocodenotas.feature_notas.domain.model

import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import java.text.DateFormat
import java.util.*

data class Nota(
    val titulo: String,
    val descricao: String,
    val imagemUrl: String = "",
    val data: String = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time),
    val cor: Int = R.color.bg_semi_dark
){
    fun toNotaEntity() : NotaEntity{
        return NotaEntity(
            titulo = titulo,
            descricao = descricao,
            imagemUrl = imagemUrl,
            cor = cor,
            data = data
        )
    }
}