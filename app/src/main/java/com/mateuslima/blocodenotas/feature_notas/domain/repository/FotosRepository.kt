package com.mateuslima.blocodenotas.feature_notas.domain.repository

import com.mateuslima.blocodenotas.feature_notas.data.remote.dto.FotoPixabayDto
import retrofit2.Response

interface FotosRepository {

    suspend fun getListaFotos(pesquisa: String, ordem: String, pagina: Int, porPagina: Int) : Response<FotoPixabayDto>
}