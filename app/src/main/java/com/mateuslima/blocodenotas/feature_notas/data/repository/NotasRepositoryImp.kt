package com.mateuslima.blocodenotas.feature_notas.data.repository

import com.mateuslima.blocodenotas.feature_notas.data.local.dao.NotaDao
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotasRepositoryImp @Inject constructor(
    val dao: NotaDao
) : NotasRepository {

    override suspend fun add(nota: NotaEntity) {
        dao.addNota(nota)
    }

    override suspend fun remover(nota: NotaEntity) {
        dao.removerNota(nota)
    }

    override suspend fun atualizar(nota: NotaEntity) {
        dao.atualizarNota(nota)
    }

    override fun getNotesOrderTitle(search: String): Flow<List<NotaEntity>>{
        return dao.getNotesOrderTitle(search)
    }

    override fun getNotesOrderColor(search: String): Flow<List<NotaEntity>>{
        return dao.getNotesOrderColor(search)
    }

    override fun getNotesOrderDate(search: String): Flow<List<NotaEntity>> {
        return dao.getNotesOrderDate(search)
    }

}