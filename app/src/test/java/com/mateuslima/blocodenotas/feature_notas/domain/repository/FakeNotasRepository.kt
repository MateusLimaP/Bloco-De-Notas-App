package com.mateuslima.blocodenotas.feature_notas.domain.repository

import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNotasRepository(
    val fakeNotas: MutableList<Nota> = emptyList<Nota>().toMutableList()
): NotasRepository {

    //private val fakeNotas = mutableListOf<Nota>()

    override suspend fun add(nota: Nota) {
        fakeNotas.add(nota)
    }

    override suspend fun remover(nota: Nota) {
        fakeNotas.remove(nota)
    }

    override suspend fun atualizar(nota: Nota) {
        fakeNotas.forEachIndexed { index, fakeNota ->
            if (fakeNota.id == nota.id) fakeNotas.set(index, nota)
        }

    }

    override fun getNotesOrderTitle(search: String): Flow<List<Nota>> = flow {
        emit(fakeNotas.sortedByDescending { it.titulo })
    }

    override fun getNotesOrderColor(search: String): Flow<List<Nota>> = flow {
        emit(fakeNotas.sortedByDescending { it.corHex  })
    }

    override fun getNotesOrderDate(search: String): Flow<List<Nota>> = flow {
       emit(fakeNotas.sortedByDescending { it.data })
    }
}