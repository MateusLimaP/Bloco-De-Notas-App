package com.mateuslima.blocodenotas.feature_notas.data.local.dao

import androidx.room.*
import com.mateuslima.blocodenotas.feature_notas.data.local.entity.NotaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNota(nota: NotaEntity)

    @Delete
    suspend fun removerNota(nota: NotaEntity)

    @Update
    suspend fun atualizarNota(nota: NotaEntity)

    @Query("SELECT * FROM nota_table")
    fun getAllNotes() : List<NotaEntity>

    @Query("SELECT * FROM nota_table WHERE titulo LIKE '%' || :search || '%' ORDER BY LOWER(titulo) ASC")
    fun getNotesOrderTitle(search: String) : Flow<List<NotaEntity>>

    @Query("SELECT * FROM nota_table WHERE titulo LIKE '%' || :search || '%' ORDER BY corHex ASC")
    fun getNotesOrderColor(search: String) : Flow<List<NotaEntity>>

    @Query("SELECT * FROM nota_table WHERE titulo LIKE '%' || :search || '%' ORDER BY data ASC")
    fun getNotesOrderDate(search: String) : Flow<List<NotaEntity>>

}