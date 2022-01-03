package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotesOrderTitleUseCase @Inject constructor(
    private val repository: NotasRepository
) {

    fun execute(pesquisa: String) : Flow<List<Nota>>{
        if (pesquisa.isEmpty())
            return emptyFlow()
        return repository.getNotesOrderTitle(pesquisa)
    }
}