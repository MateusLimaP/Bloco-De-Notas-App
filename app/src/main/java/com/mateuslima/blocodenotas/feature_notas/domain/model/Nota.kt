package com.mateuslima.blocodenotas.feature_notas.domain.model

import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity

data class Nota(
    val titulo: String,
    val descricao: String,
    val imagemUrl: String,
    val data: String,
    val cor: Int
){
    fun toNotaEntity() : NotaEntity{
        return NotaEntity(
            titulo = titulo,
            descricao = descricao,
            imagemUrl = imagemUrl,
            cor = cor
        )
    }
}