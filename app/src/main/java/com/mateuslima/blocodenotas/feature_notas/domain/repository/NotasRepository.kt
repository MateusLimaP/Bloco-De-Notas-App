package com.mateuslima.blocodenotas.feature_notas.domain.repository

import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import kotlinx.coroutines.flow.Flow

interface NotasRepository {

    suspend fun add(nota: Nota)
    suspend fun remover(nota: Nota)
    suspend fun atualizar(nota: Nota)
    fun getNotesOrderTitle(search: String) : Flow<List<Nota>>
    fun getNotesOrderColor(search: String) : Flow<List<Nota>>
    fun getNotesOrderDate(search: String) : Flow<List<Nota>>
}