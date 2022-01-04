package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.mateuslima.blocodenotas.feature_notas.domain.repository.FotosRepository
import javax.inject.Inject

class GetListaFotosUseCase @Inject constructor(
    private val repository: FotosRepository
) {

    suspend fun execute(pesquisa: String = "") =
        repository.getListaFotos(pesquisa)

}