package com.mateuslima.blocodenotas.feature_notas.data.repository

import com.mateuslima.blocodenotas.feature_notas.data.local.dao.NotaDao
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
        dao.addNota(nota.toNotaEntity())
    }

    override suspend fun remover(nota: Nota) {
        dao.removerNota(nota.toNotaEntity())
    }

    override suspend fun atualizar(nota: Nota) {
        dao.atualizarNota(nota.toNotaEntity())
    }

    override fun getNotesOrderTitle(search: String): Flow<List<Nota>> = flow {
       emit(dao.getNotesOrderTitle(search).map { it.toNota() })
    }

    override fun getNotesOrderColor(search: String): Flow<List<Nota>> = flow {
        emit(dao.getNotesOrderColor(search).map { it.toNota() })
    }

    override fun getNotesOrderDate(search: String): Flow<List<Nota>> = flow {
        emit(dao.getNotesOrderDate(search).map { it.toNota() })
    }

}