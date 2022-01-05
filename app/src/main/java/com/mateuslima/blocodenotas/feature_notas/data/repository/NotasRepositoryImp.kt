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

    override suspend fun add(nota: Nota) {
        val notaEntity = NotaEntity.copyNota(nota)
        dao.addNota(notaEntity)
    }

    override suspend fun remover(nota: Nota) {
        dao.removerNota(NotaEntity.copyNota(nota))
    }

    override suspend fun atualizar(nota: Nota) {
        dao.atualizarNota(NotaEntity.copyNota(nota))
    }

    override fun getNotesOrderTitle(search: String): Flow<List<Nota>>{
        return dao.getNotesOrderTitle(search).map { it.map { it.toNota() } }
    }

    override fun getNotesOrderColor(search: String): Flow<List<Nota>>{
        return dao.getNotesOrderColor(search).map { it.map { it.toNota() } }
    }

    override fun getNotesOrderDate(search: String): Flow<List<Nota>> {
        return dao.getNotesOrderDate(search).map { it.map { it.toNota() } }
    }

}