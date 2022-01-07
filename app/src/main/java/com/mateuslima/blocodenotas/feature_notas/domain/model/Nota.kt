package com.mateuslima.blocodenotas.feature_notas.domain.model

import android.os.Parcelable
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.util.*

@Parcelize
data class Nota(
    val titulo: String,
    val descricao: String,
    val imagemUrl: String,
    val data: String,
    val corHex: String,
    val id: Int = 0
) : Parcelable