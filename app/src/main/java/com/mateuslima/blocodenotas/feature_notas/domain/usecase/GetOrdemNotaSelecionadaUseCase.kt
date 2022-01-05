package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import javax.inject.Inject

class GetOrdemNotaSelecionadaUseCase @Inject constructor(
    private val repository: NotasPrefsRepository
) {

    fun execute() = repository.getOrdemNotaSelecionada()
}