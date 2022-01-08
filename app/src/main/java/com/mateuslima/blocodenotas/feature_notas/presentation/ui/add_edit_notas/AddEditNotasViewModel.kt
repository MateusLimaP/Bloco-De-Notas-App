package com.mateuslima.blocodenotas.feature_notas.presentation.ui.add_edit_notas

import androidx.lifecycle.*
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.AddNotaUseCase
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.AtualizarNotaUseCase
import com.mateuslima.blocodenotas.feature_notas.domain.usecase.SaveOrUpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNotasViewModel @Inject constructor(
    private val addNotaUseCase: AddNotaUseCase,
    private val atualizarNotaUseCase: AtualizarNotaUseCase,
    private val saveOrUpdateNoteUseCase: SaveOrUpdateNoteUseCase,
    private val args: SavedStateHandle
) : ViewModel() {

    private val notaRecuperada = args.get<Nota>("nota")
    var tituloNota = notaRecuperada?.titulo ?: ""
    var descricaoNota = notaRecuperada?.descricao ?: ""
    var imagemNota = notaRecuperada?.imagemUrl ?: ""
    var corNota = notaRecuperada?.corHex ?: "#1a212f"

    private val _imagemNotaObsevable = MutableLiveData(imagemNota)
    val imagemNotaObservable: LiveData<String> get() = _imagemNotaObsevable

    // enviar eventos e atualizar a ui de acordo
    private val salvarNotaEventChannel = Channel<SaveOrUpdateNoteUseCase.Result>()
    val salvarNotaEvent = salvarNotaEventChannel.receiveAsFlow().asLiveData()

    fun salvarNota(nota: Nota) = viewModelScope.launch {
        saveOrUpdateNoteUseCase.execute(notaRecuperada, nota).also { result ->
            salvarNotaEventChannel.send(result)
        }

    }

    fun setImagemNotaUrl(imagemUrl: String){
        _imagemNotaObsevable.postValue(imagemUrl)
        imagemNota = imagemUrl
    }


}