package com.mateuslima.blocodenotas.feature_notas.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mateuslima.blocodenotas.feature_notas.data.remote.FotoPixabayApi
import com.mateuslima.blocodenotas.feature_notas.data.remote.dto.FotoPixabayDto
import com.mateuslima.blocodenotas.feature_notas.domain.model.Foto
import retrofit2.HttpException
import java.io.IOException

class FotosPagingSource(
    val pesquisa: String = "",
    val api: FotoPixabayApi
) : PagingSource<Int, FotoPixabayDto.Hit>() {



    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FotoPixabayDto.Hit> {
        return try {
            val position = params.key ?: 1
            val response = api.getListaFotos(pesquisa = pesquisa, ordem = "popular", pagina = position, porPagina = params.loadSize)
            val listaFotosDto = response.body()?.hits ?: emptyList()
            LoadResult.Page(
                data = listaFotosDto,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (listaFotosDto.isEmpty()) null else position + 1
            )

        }catch (e: IOException){ // falha na rede
            LoadResult.Error(e)

        }catch (e: HttpException){ // falha no servidor
            LoadResult.Error(e)

        }catch (e: Exception){
            LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, FotoPixabayDto.Hit>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}