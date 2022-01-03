package com.mateuslima.blocodenotas.feature_notas.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
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

    val ordemNota = context.dataStore.data.map { preferences ->
        val ordem = OrganizarNota.valueOf(preferences[ORDEM_NOTA] ?: OrganizarNota.TITULO.name)
        ordem
    }


    suspend fun atualizarOrdem(organizarNota: OrganizarNota){
        context.dataStore.edit { edit ->
            edit[ORDEM_NOTA] = organizarNota.name
        }
    }

    enum class OrganizarNota{COR, TITULO, DATA}
}