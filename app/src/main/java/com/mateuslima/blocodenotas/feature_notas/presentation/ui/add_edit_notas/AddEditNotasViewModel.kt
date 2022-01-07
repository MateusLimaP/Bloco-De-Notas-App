package com.mateuslima.blocodenotas.feature_notas.presentation.ui.add_edit_notas

import androidx.lifecycle.*
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.AddNotaUseCase
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.AtualizarNotaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNotasViewModel @Inject constructor(
    private val addNotaUseCase: AddNotaUseCase,
    private val atualizarNotaUseCase: AtualizarNotaUseCase,
    private val args: SavedStateHandle
) : ViewModel() {

    private val notaRecuperada = args.get<Nota>("nota")
    var tituloNota = notaRecuperada?.titulo ?: ""
    var descricaoNota = notaRecuperada?.descricao ?: ""
    var imagemNota = notaRecuperada?.imagemUrl ?: ""
    var corNota = notaRecuperada?.corHex ?: "#1a212f"

    private val _imagemNotaObsevable = MutableLiveData(imagemNota)
    val imagemNotaObservable: LiveData<String> get() = _imagemNotaObsevable

    fun salvarNota(nota: Nota) = viewModelScope.launch {
        if (notaRecuperada == null) addNotaUseCase.execute(nota)
        else atualizarNotaUseCase.execute(nota.copy(id = notaRecuperada.id))
    }

    fun setImagemNotaUrl(imagemUrl: String){
        _imagemNotaObsevable.postValue(imagemUrl)
        imagemNota = imagemUrl
    }


}