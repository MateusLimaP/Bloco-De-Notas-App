package com.mateuslima.blocodenotas.feature_notas.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotasPrefsRepositoryImp @Inject constructor(
    @ApplicationContext private val context: Context
) : NotasPrefsRepository{

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("notas_prefs")

    private val ORDEM_NOTA = stringPreferencesKey("ordem_nota")
    private val FOTO_PERFIL = stringPreferencesKey("foto_perfil")


    override suspend fun atualizarFoto(url: String) {
        context.dataStore.edit { edit ->
            edit[FOTO_PERFIL] = url
        }
    }

    override suspend fun atualizarOrdemNota(organizarNota: NotasPrefsRepository.OrganizarNota) {
        context.dataStore.edit { edit ->
            edit[ORDEM_NOTA] = organizarNota.name
        }
    }

    override fun getOrdemNotaSelecionada(): Flow<NotasPrefsRepository.OrganizarNota> {
       return context.dataStore.data.map { preference ->
           val ordem = NotasPrefsRepository.OrganizarNota.valueOf(
               preference[ORDEM_NOTA] ?: NotasPrefsRepository.OrganizarNota.TITULO.name)
           ordem
       }
    }

    override fun getFotoPerfil(): Flow<String> {
        return context.dataStore.data.map { preference ->
            preference[FOTO_PERFIL] ?: ""
        }
    }
}