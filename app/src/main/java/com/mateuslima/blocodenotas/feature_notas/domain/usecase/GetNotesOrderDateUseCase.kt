package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class GetNotesOrderDateUseCase @Inject constructor(
    private val repository: NotasRepository
) {

    fun execute(pesquisa: String) : Flow<List<Nota>>{
        if (pesquisa.isEmpty())
            return emptyFlow()
        return repository.getNotesOrderDate(pesquisa)
    }
}