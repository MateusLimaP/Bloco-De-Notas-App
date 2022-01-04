package com.mateuslima.blocodenotas.feature_notas.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mateuslima.blocodenotas.feature_notas.data.remote.dto.FotoPixabayDto
import com.mateuslima.blocodenotas.feature_notas.domain.model.Foto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface FotosRepository {

    suspend fun getListaFotos(pesquisa: String) : Flow<PagingData<Foto>>
}