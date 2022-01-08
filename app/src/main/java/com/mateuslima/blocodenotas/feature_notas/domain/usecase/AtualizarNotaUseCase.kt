package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import javax.inject.Inject

class AtualizarNotaUseCase @Inject constructor(
    private val repository: NotasRepository
) {

    suspend fun execute(nota: Nota): Result{
        if (nota.titulo.isEmpty()) return Result.CampoTituloVazio
        if (nota.descricao.isEmpty()) return Result.CampoDescricaoVazio
        repository.atualizar(nota)
        return Result.Sucesso
    }

    sealed class Result{
        object CampoTituloVazio: Result()
        object CampoDescricaoVazio: Result()
        object Sucesso: Result()
    }
}