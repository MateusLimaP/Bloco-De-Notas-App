package com.mateuslima.blocodenotas.feature_notas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mateuslima.blocodenotas.feature_notas.data.local.dao.NotaDao
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity

@Database(entities = [NotaEntity::class], version = 1)
abstract class NotaDatabase : RoomDatabase() {

    abstract fun notaDao() : NotaDao
}