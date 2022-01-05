package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import javax.inject.Inject

class AtualizarOrdemNotaUseCase @Inject constructor(
    private val repository: NotasPrefsRepository
) {

   suspend fun execute(ordem: NotasPrefsRepository.OrganizarNota){
       repository.atualizarOrdemNota(ordem)
   }
}