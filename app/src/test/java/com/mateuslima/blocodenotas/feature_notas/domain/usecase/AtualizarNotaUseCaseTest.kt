package com.mateuslima.blocodenotas.feature_notas.domain.usecase

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.FakeNotasRepository
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AtualizarNotaUseCaseTest{

    private lateinit var atualizarNotaUseCase: AtualizarNotaUseCase

    @Before
    fun setup(){
        atualizarNotaUseCase = AtualizarNotaUseCase(FakeNotasRepository())
    }

    @Test
    fun `campo titulo e descricao preenchido, return success`() = runBlockingTest{
        val nota = Nota(titulo = "titulo", descricao = "descricao", imagemUrl = "","","")
        val result = atualizarNotaUseCase.execute(nota)
        assertThat(result).isEqualTo(AtualizarNotaUseCase.Result.Sucesso)
    }

    @Test
    fun `campo titulo nao preenchido, return error`() = runBlockingTest {
        val nota = Nota(titulo = "", descricao = "descricao", imagemUrl = "","","")
        val result = atualizarNotaUseCase.execute(nota)
        assertThat(result).isEqualTo(AtualizarNotaUseCase.Result.CampoTituloVazio)
    }

    @Test
    fun `campo descricao nao preenchido, return error`() = runBlockingTest {
        val nota = Nota(titulo = "titulo", descricao = "", imagemUrl = "","","")
        val result = atualizarNotaUseCase.execute(nota)
        assertThat(result).isEqualTo(AtualizarNotaUseCase.Result.CampoDescricaoVazio)
    }
}