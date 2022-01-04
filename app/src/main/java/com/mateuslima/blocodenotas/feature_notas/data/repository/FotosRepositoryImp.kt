package com.mateuslima.blocodenotas.feature_notas.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.*
import com.mateuslima.blocodenotas.feature_notas.data.remote.FotoPixabayApi
import com.mateuslima.blocodenotas.feature_notas.data.remote.dto.FotoPixabayDto
import com.mateuslima.blocodenotas.feature_notas.data.remote.paging.FotosPagingSource
import com.mateuslima.blocodenotas.feature_notas.domain.model.Foto
import com.mateuslima.blocodenotas.feature_notas.domain.repository.FotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FotosRepositoryImp @Inject constructor(
    private val api: FotoPixabayApi
) : FotosRepository {

    override suspend fun getListaFotos(pesquisa: String): Flow<PagingData<Foto>> {
        return Pager(
            config = PagingConfig(pageSize = 25, maxSize = 100, enablePlaceholders = false),
            pagingSourceFactory = {FotosPagingSource(pesquisa, api)}
        ).flow.map { it.map { it.toFoto() } }
    }


}