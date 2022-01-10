package com.mateuslima.blocodenotas.feature_notas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.di.SingletonCoroutine
import com.mateuslima.blocodenotas.feature_notas.data.local.dao.NotaDao
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Database(entities = [NotaEntity::class], version = 2)
abstract class NotaDatabase : RoomDatabase() {

    abstract fun notaDao() : NotaDao

    @Singleton
    class Callback @Inject constructor(
        private val dao: Provider<NotaDao>,
        @ApplicationContext private val context: Context,
        @SingletonCoroutine private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch {
                val auroraPolarUrl = "https://cdn.pixabay.com/photo/2016/02/09/19/57/aurora-1190254__340.jpg"

                dao.get().addNota(NotaEntity(titulo = context.getString(R.string.nota1_titulo), descricao = context.getString(R.string.nota1_msg), imagemUrl = auroraPolarUrl))
                dao.get().addNota(NotaEntity(titulo = context.getString(R.string.nota2_titulo), descricao = context.getString(R.string.nota2_msg)))
                dao.get().addNota(NotaEntity(titulo = context.getString(R.string.nota3_titulo), descricao = context.getString(R.string.nota3_msg)))
                dao.get().addNota(NotaEntity(titulo = context.getString(R.string.nota4_titulo), descricao = context.getString(R.string.nota4_msg)))
                dao.get().addNota(NotaEntity(titulo = context.getString(R.string.nota5_titulo), descricao = context.getString(R.string.nota5_msg)))



            }

        }
    }
}