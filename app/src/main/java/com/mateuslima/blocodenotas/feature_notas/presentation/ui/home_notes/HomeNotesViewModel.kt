package com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mateuslima.blocodenotas.feature_notas.data.local.preferences.NotasPreferences
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.GetNotesOrderColorUseCase
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.GetNotesOrderDateUseCase
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.GetNotesOrderTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeNotesViewModel @Inject constructor(
    private val notasPreferences: NotasPreferences,
    private val getNotesOrderTitleUseCase: GetNotesOrderTitleUseCase,
    private val getNotesOrderColorUseCase: GetNotesOrderColorUseCase,
    private val getNotesOrderDateUseCase: GetNotesOrderDateUseCase
) : ViewModel() {

    val pesquisa = MutableStateFlow("")
    val ordemSelecionada = notasPreferences.ordemNota

    val listaNotas = combine(pesquisa, notasPreferences.ordemNota){ search, ordem ->
        Wrapper(search, ordem)
    }.flatMapLatest { (search, ordem) ->
        when(ordem){
            NotasPreferences.OrganizarNota.TITULO -> getNotesOrderTitleUseCase.execute(search)
            NotasPreferences.OrganizarNota.DATA -> getNotesOrderDateUseCase.execute(search)
            NotasPreferences.OrganizarNota.COR -> getNotesOrderColorUseCase.execute(search)
        }
    }.asLiveData()

    fun organizarPorTitulo() = viewModelScope.launch{
        notasPreferences.atualizarOrdem(NotasPreferences.OrganizarNota.TITULO)
    }

    fun organizarPorData() = viewModelScope.launch{
        notasPreferences.atualizarOrdem(NotasPreferences.OrganizarNota.DATA)
    }

    fun organizarPorCor() = viewModelScope.launch{
        notasPreferences.atualizarOrdem(NotasPreferences.OrganizarNota.COR)
    }

    data class Wrapper<T1, T2>(val t1: T1, val t2: T2)
}