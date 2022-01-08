package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import javax.inject.Inject

class SaveOrUpdateNoteUseCase @Inject constructor(
    private val repository: NotasRepository
) {

    suspend fun execute(recorveredNoteArgument: Nota?, noteToSaveOrUpdate: Nota) : Result{
        if(noteToSaveOrUpdate.titulo.isEmpty()) return Result.CampoTituloVazio
        if (noteToSaveOrUpdate.descricao.isEmpty()) return Result.CampoDescricaoVazio

        if (recorveredNoteArgument == null){
            repository.add(noteToSaveOrUpdate)
            return Result.NotaSalva(noteToSaveOrUpdate)
        }
        repository.atualizar(noteToSaveOrUpdate.copy(id = recorveredNoteArgument.id))
        return Result.NotaAtualizada(noteToSaveOrUpdate)
    }

    sealed class Result(val nota: Nota? = null){
        class NotaSalva(nota: Nota): Result(nota)
        class NotaAtualizada(nota: Nota): Result(nota)
        object CampoTituloVazio: Result()
        object CampoDescricaoVazio: Result()
    }
}