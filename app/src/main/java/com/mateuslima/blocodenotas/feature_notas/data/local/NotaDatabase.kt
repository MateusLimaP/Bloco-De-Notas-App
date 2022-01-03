package com.mateuslima.blocodenotas.feature_notas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mateuslima.blocodenotas.core.di.SingletonCoroutine
import com.mateuslima.blocodenotas.feature_notas.data.local.dao.NotaDao
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Database(entities = [NotaEntity::class], version = 1)
abstract class NotaDatabase : RoomDatabase() {

    abstract fun notaDao() : NotaDao

    @Singleton
    class Callback @Inject constructor(
        private val dao: Provider<NotaDao>,
        @SingletonCoroutine private val applicationScope: CoroutineScope
    ): RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            applicationScope.launch {

                dao.get().addNota(Nota(titulo = "Titulo1", descricao = "descricao").toNotaEntity())
                dao.get().addNota(Nota(titulo = "Titulo2", descricao = "descricao").toNotaEntity())
                dao.get().addNota(Nota(titulo = "Titulo3", descricao = "descricao").toNotaEntity())
                dao.get().addNota(Nota(titulo = "Titulo4", descricao = "descricao").toNotaEntity())
                dao.get().addNota(Nota(titulo = "Titulo5", descricao = "descricao").toNotaEntity())
                dao.get().addNota(Nota(titulo = "Titulo6", descricao = "descricao").toNotaEntity())

            }

        }
    }
}