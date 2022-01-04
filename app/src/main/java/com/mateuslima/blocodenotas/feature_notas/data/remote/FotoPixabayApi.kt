package com.mateuslima.blocodenotas.feature_notas.data.remote

import com.mateuslima.blocodenotas.BuildConfig
import com.mateuslima.blocodenotas.feature_notas.data.remote.dto.FotoPixabayDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FotoPixabayApi {

    companion object{
        const val BASE_URL = "https://pixabay.com/"
    }

    @GET("api/")
    suspend fun getListaFotos(
        @Query("key") apiKey: String = BuildConfig.API_KEY_PIXABAY,
        @Query("q") pesquisa: String = "",
        @Query("order") ordem: String,  // popular - latest
        @Query("page") pagina: Int,
        @Query("per_page") porPagina: Int
    ) : Response<FotoPixabayDto>
}