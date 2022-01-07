package com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeNotesViewModel @Inject constructor(
    private val getNotesOrderTitleUseCase: GetNotesOrderTitleUseCase,
    private val getNotesOrderColorUseCase: GetNotesOrderColorUseCase,
    private val getNotesOrderDateUseCase: GetNotesOrderDateUseCase,
    private val getOrdemNotaSelecionadaUseCase: GetOrdemNotaSelecionadaUseCase,
    private val getFotoPerfilUseCase: GetFotoPerfilUseCase,
    private val atualizarFotoPerfilUseCase: AtualizarFotoPerfilUseCase,
    private val atualizarOrdemNotaUseCase: AtualizarOrdemNotaUseCase,
    private val atualizarNotaUseCase: AtualizarNotaUseCase,
    private val removerNotaUseCase: RemoverNotaUseCase,
    private val addNotaUseCase: AddNotaUseCase
) : ViewModel() {

    val pesquisa = MutableStateFlow("")
    val ordemSelecionada = getOrdemNotaSelecionadaUseCase.execute()
    val fotoPerfil = getFotoPerfilUseCase.execute().asLiveData()

    val listaNotas = combine(pesquisa, ordemSelecionada){ search, ordem ->
        Wrapper(search, ordem)
    }.flatMapLatest { (search, ordem) ->
        when(ordem){
            NotasPrefsRepository.OrganizarNota.TITULO -> getNotesOrderTitleUseCase.execute(search)
            NotasPrefsRepository.OrganizarNota.DATA -> getNotesOrderDateUseCase.execute(search)
            NotasPrefsRepository.OrganizarNota.COR -> getNotesOrderColorUseCase.execute(search)
        }
    }.asLiveData()

    fun organizarPorTitulo() = viewModelScope.launch{
       atualizarOrdemNotaUseCase.execute(NotasPrefsRepository.OrganizarNota.TITULO)
    }

    fun organizarPorData() = viewModelScope.launch{
        atualizarOrdemNotaUseCase.execute(NotasPrefsRepository.OrganizarNota.DATA)
    }

    fun organizarPorCor() = viewModelScope.launch{
        atualizarOrdemNotaUseCase.execute(NotasPrefsRepository.OrganizarNota.COR)
    }

    fun salvarFotoPerfil(imagemUrl: String) = viewModelScope.launch {
        atualizarFotoPerfilUseCase.execute(imagemUrl)
    }

    fun atualizarCorNota(corHex: String, nota: Nota) = viewModelScope.launch {
        atualizarNotaUseCase.execute(nota.copy(corHex = corHex))
    }

    fun deletarNota(nota: Nota) = viewModelScope.launch {
        removerNotaUseCase.execute(nota)
    }

    fun copiarNota(nota: Nota) = viewModelScope.launch {
        addNotaUseCase.execute(nota.copy(id = 0)) // adiciona o mesmos valores em outra tabela sem sobrescrevê-los
    }

    data class Wrapper<T1, T2>(val t1: T1, val t2: T2)
}