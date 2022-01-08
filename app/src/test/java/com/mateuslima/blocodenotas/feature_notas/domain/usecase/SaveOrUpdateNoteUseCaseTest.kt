package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.FakeNotasRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SaveOrUpdateNoteUseCaseTest{


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var saveOrUpdateNoteUseCase: SaveOrUpdateNoteUseCase

    @Before
    fun setup(){
        saveOrUpdateNoteUseCase = SaveOrUpdateNoteUseCase(FakeNotasRepository())
    }


    @Test
    fun `nota argumento nulo, return salvar nota`() = runBlockingTest{
        val nota = Nota("titulo","descricao","","","")
        val result = saveOrUpdateNoteUseCase.execute(null, nota)
        assertThat(result).isInstanceOf(SaveOrUpdateNoteUseCase.Result.NotaSalva::class.java)
    }

    @Test
    fun `nota argumento recuperado, return atualizar nota`() = runBlockingTest{
        val nota = Nota("titulo","descricao","","","")
        val notaArgs = Nota("titulo2","descricao2","","","")
        val result = saveOrUpdateNoteUseCase.execute(notaArgs, nota)
        assertThat(result).isInstanceOf(SaveOrUpdateNoteUseCase.Result.NotaAtualizada::class.java)
    }

    @Test
    fun `campo titulo nao preenchido, return erro empty field`() = runBlockingTest{
        val nota = Nota("","descricao","","","")
        val result = saveOrUpdateNoteUseCase.execute(null, nota)
        assertThat(result).isEqualTo(SaveOrUpdateNoteUseCase.Result.CampoTituloVazio)
    }

    @Test
    fun `campo descricao nao preenchido, return erro empty field`() = runBlockingTest{
        val nota = Nota("titulo","","","","")
        val result = saveOrUpdateNoteUseCase.execute(null, nota)
        assertThat(result).isEqualTo(SaveOrUpdateNoteUseCase.Result.CampoDescricaoVazio)
    }
}