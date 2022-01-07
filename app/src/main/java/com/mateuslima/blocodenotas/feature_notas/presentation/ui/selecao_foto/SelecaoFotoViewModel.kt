package com.mateuslima.blocodenotas.feature_notas.presentation.ui.selecao_foto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.GetListaFotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SelecaoFotoViewModel @Inject constructor(
    private val getListaFotosUseCase: GetListaFotosUseCase,
    private val args: SavedStateHandle
) : ViewModel() {

    val backStackFragmentName = args.get<String>("fragmentname")!!

    val pesquisa = MutableStateFlow("")
    val listaFoto = pesquisa.flatMapLatest { search ->
        getListaFotosUseCase.execute(search).cachedIn(viewModelScope)
    }.asLiveData()

}