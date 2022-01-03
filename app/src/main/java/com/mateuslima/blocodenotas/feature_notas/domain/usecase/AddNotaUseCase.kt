package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import javax.inject.Inject

class AddNotaUseCase @Inject constructor(
    val repository: NotasRepository
) {

    suspend fun execute(nota: Nota){
        repository.add(nota)
    }
}