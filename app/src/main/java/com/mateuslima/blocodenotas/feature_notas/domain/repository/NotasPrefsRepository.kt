package com.mateuslima.blocodenotas.feature_notas.domain.repository

import kotlinx.coroutines.flow.Flow

interface NotasPrefsRepository {

    fun getOrdemNotaSelecionada() : Flow<OrganizarNota>
    fun getFotoPerfil() : Flow<String>
    suspend fun atualizarFoto(url: String)
    suspend fun atualizarOrdemNota(organizarNota: OrganizarNota)

    enum class OrganizarNota{COR, TITULO, DATA}
}