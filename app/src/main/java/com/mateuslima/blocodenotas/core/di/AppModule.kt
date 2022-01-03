package com.mateuslima.blocodenotas.core.di

import android.app.Application
import androidx.room.Room
import com.mateuslima.blocodenotas.feature_notas.data.local.NotaDatabase
import com.mateuslima.blocodenotas.feature_notas.data.local.dao.NotaDao
import com.mateuslima.blocodenotas.feature_notas.data.repository.NotasRepositoryImp
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNotaDatabase(application: Application) : NotaDatabase{
        return Room.databaseBuilder(application, NotaDatabase::class.java, "nota_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideNotaDao(database: NotaDatabase) : NotaDao{
        return database.notaDao()
    }

    @Singleton
    @Provides
    fun provideNotaRepository(repository: NotasRepositoryImp) : NotasRepository{
        return repository
    }
}