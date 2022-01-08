package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.FakeNotasRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AddNotaUseCaseTest{

    private lateinit var addNotaUseCase: AddNotaUseCase

    @Before
    fun setup(){
        addNotaUseCase = AddNotaUseCase(FakeNotasRepository())
    }


    @Test
    fun `campo titulo e descricao preenchido, return success`() = runBlockingTest{
        val nota = Nota(titulo = "titulo", descricao = "descricao", imagemUrl = "","","")
        val result = addNotaUseCase.execute(nota)
        assertThat(result).isEqualTo(AddNotaUseCase.Result.Sucesso)
    }

    @Test
    fun `campo titulo nao preenchido, return error`() = runBlockingTest {
        val nota = Nota(titulo = "", descricao = "descricao", imagemUrl = "","","")
        val result = addNotaUseCase.execute(nota)
        assertThat(result).isEqualTo(AddNotaUseCase.Result.CampoTituloVazio)
    }

    @Test
    fun `campo descricao nao preenchido, return error`() = runBlockingTest {
        val nota = Nota(titulo = "titulo", descricao = "", imagemUrl = "","","")
        val result = addNotaUseCase.execute(nota)
        assertThat(result).isEqualTo(AddNotaUseCase.Result.CampoDescricaoVazio)
    }


}