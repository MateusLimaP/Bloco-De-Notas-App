package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import javax.inject.Inject

class GetFotoPerfilUseCase @Inject constructor(
    private val repository: NotasPrefsRepository
) {

    fun execute() = repository.getFotoPerfil()
}