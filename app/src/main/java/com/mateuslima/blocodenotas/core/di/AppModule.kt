package com.mateuslima.blocodenotas.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mateuslima.blocodenotas.feature_notas.data.local.NotaDatabase
import com.mateuslima.blocodenotas.feature_notas.data.local.dao.NotaDao
import com.mateuslima.blocodenotas.feature_notas.data.remote.FotoPixabayApi
import com.mateuslima.blocodenotas.feature_notas.data.repository.FotosRepositoryImp
import com.mateuslima.blocodenotas.feature_notas.data.repository.NotasPrefsRepositoryImp
import com.mateuslima.blocodenotas.feature_notas.data.repository.NotasRepositoryImp
import com.mateuslima.blocodenotas.feature_notas.domain.repository.FotosRepository
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNotaDatabase(application: Application,
                            callback: NotaDatabase.Callback) : NotaDatabase{
        return Room.databaseBuilder(application, NotaDatabase::class.java, "nota_db")
            .addCallback(callback)
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

    @SingletonCoroutine
    @Singleton
    @Provides
    fun provideCoroutineScope() : CoroutineScope{
        return CoroutineScope(SupervisorJob())
    }

    @Singleton
    @Provides
    fun provideRetrofitPixabay() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(FotoPixabayApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideFotoPixabayApi(retrofit: Retrofit) : FotoPixabayApi{
        return retrofit.create(FotoPixabayApi::class.java)
    }

    @Singleton
    @Provides
    fun provideFotosRepository(fotosRepository: FotosRepositoryImp) : FotosRepository{
        return fotosRepository
    }

    @Singleton
    @Provides
    fun provideNotasPrefsRepository(notasPrefsRepository: NotasPrefsRepositoryImp) : NotasPrefsRepository{
        return notasPrefsRepository
    }
}