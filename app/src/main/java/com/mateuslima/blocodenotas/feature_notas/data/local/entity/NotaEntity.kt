package com.mateuslima.blocodenotas.feature_notas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import java.text.DateFormat
import java.util.*

@Entity(tableName = "nota_table")
data class NotaEntity(
    val titulo: String,
    val descricao: String,
    val imagemUrl: String = "",
    val data: String = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time),
    val cor: Int = R.color.bg_semi_dark,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
){
    fun toNota() : Nota{
        return Nota(
            titulo = titulo,
            descricao = descricao,
            imagemUrl = imagemUrl,
            data = data,
            cor = cor
        )
    }
    companion object{
        fun copyNota(nota: Nota) : NotaEntity{
            return NotaEntity(
                titulo = nota.titulo,
                descricao = nota.descricao,
                imagemUrl = nota.imagemUrl,
                cor = nota.cor
            )
        }
    }

}