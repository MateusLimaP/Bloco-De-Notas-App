package com.mateuslima.blocodenotas.feature_notas.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mateuslima.blocodenotas.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotasPreferences @Inject constructor(
   @ApplicationContext private val context: Context
) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore("notas_prefs")

    private val ORDEM_NOTA = stringPreferencesKey("ordem_nota")
    private val FOTO_PERFIL = stringPreferencesKey("foto_perfil")

    val ordemNota = context.dataStore.data.map { preferences ->
        val ordem = OrganizarNota.valueOf(preferences[ORDEM_NOTA] ?: OrganizarNota.TITULO.name)
        ordem
    }

    val fotoPerfil = context.dataStore.data.map { preferences ->
        preferences[FOTO_PERFIL] ?: ""
    }


    suspend fun atualizarOrdem(organizarNota: OrganizarNota){
        context.dataStore.edit { edit ->
            edit[ORDEM_NOTA] = organizarNota.name
        }
    }

    suspend fun atualizarFoto(url: String){
        context.dataStore.edit { edit ->
            edit[FOTO_PERFIL] = url
        }
    }

    enum class OrganizarNota{COR, TITULO, DATA}
}