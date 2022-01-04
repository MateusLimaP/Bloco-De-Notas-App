package com.mateuslima.blocodenotas.feature_notas.data.remote.dto

import com.mateuslima.blocodenotas.feature_notas.domain.model.Foto

data class FotoPixabayDto(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
) {
    data class Hit(
        val collections: Int,
        val comments: Int,
        val downloads: Int,
        val id: Int,
        val imageHeight: Int,
        val imageSize: Int,
        val imageWidth: Int,
        val largeImageURL: String,
        val likes: Int,
        val pageURL: String,
        val previewHeight: Int,
        val previewURL: String,
        val previewWidth: Int,
        val tags: String,
        val type: String,
        val user: String,
        val userImageURL: String,
        val user_id: Int,
        val views: Int,
        val webformatHeight: Int,
        val webformatURL: String,
        val webformatWidth: Int
    ){
        fun toFoto() : Foto{
            return Foto(
                imagemPequenaUrl = webformatURL,
                imagemGrandeUrl = largeImageURL,
                nomeUsuario = user
            )
        }
    }
}