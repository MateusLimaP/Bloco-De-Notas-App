package com.mateuslima.blocodenotas.feature_notas.domain.repository

import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import kotlinx.coroutines.flow.Flow

interface NotasRepository {

    suspend fun add(nota: NotaEntity)
    suspend fun remover(nota: NotaEntity)
    suspend fun atualizar(nota: NotaEntity)
    fun getNotesOrderTitle(search: String) : Flow<List<NotaEntity>>
    fun getNotesOrderColor(search: String) : Flow<List<NotaEntity>>
    fun getNotesOrderDate(search: String) : Flow<List<NotaEntity>>
}