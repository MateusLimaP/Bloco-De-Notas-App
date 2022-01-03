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

    @Query("SELECT * FROM nota_table WHERE titulo LIKE '%' || :search || '%' ORDER BY titulo DESC")
    fun getNotesOrderTitle(search: String) : List<NotaEntity>

    @Query("SELECT * FROM nota_table WHERE titulo LIKE '%' || :search || '%' ORDER BY cor DESC")
    fun getNotesOrderColor(search: String) : List<NotaEntity>

    @Query("SELECT * FROM nota_table WHERE titulo LIKE '%' || :search || '%' ORDER BY data DESC")
    fun getNotesOrderDate(search: String) : List<NotaEntity>

}