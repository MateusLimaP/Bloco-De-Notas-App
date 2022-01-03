package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesOrderTitleUseCase @Inject constructor(
    private val repository: NotasRepository
) {

    fun execute(pesquisa: String) : Flow<List<NotaEntity>>{
        return repository.getNotesOrderTitle(pesquisa)
    }
}