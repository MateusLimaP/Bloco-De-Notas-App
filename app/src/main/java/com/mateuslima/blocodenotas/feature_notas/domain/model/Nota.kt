package com.mateuslima.blocodenotas.feature_notas.domain.model

import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import java.text.DateFormat
import java.util.*

data class Nota(
    val titulo: String,
    val descricao: String,
    val imagemUrl: String,
    val data: String,
    val cor: Int
)