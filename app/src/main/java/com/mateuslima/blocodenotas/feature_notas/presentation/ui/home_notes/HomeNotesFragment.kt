package com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.util.setOnQueryTextChange
import com.mateuslima.blocodenotas.databinding.FragmentHomeNotesBinding
import com.mateuslima.blocodenotas.feature_notas.data.local.preferences.NotasPreferences
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.NotasAdapter
import com.mateuslima.blocodenotas.feature_notas.presentation.util.BottomSheetFoto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class HomeNotesFragment : Fragment(R.layout.fragment_home_notes), NotasAdapter.NotasAdapterListener,
BottomSheetFoto.BottomSheetFotoListener{

    private var _binding: FragmentHomeNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeNotesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeNotesBinding.bind(view)

        binding.radiogroup.setOnCheckedChangeListener { radioGroup, id ->
            when (id){
                binding.radioCor.id -> viewModel.organizarPorCor()
                binding.radioData.id -> viewModel.organizarPorData()
                binding.radioTitulo.id -> viewModel.organizarPorTitulo()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val ordem = viewModel.ordemSelecionada.first()
            when (ordem){
                NotasPreferences.OrganizarNota.TITULO -> binding.radioTitulo.isChecked = true
                NotasPreferences.OrganizarNota.COR -> binding.radioCor.isChecked = true
                NotasPreferences.OrganizarNota.DATA -> binding.radioData.isChecked = true
            }
        }

        binding.searchview.setOnQueryTextChange { search -> viewModel.pesquisa.value = search }

        val adapter = NotasAdapter(this)

        viewModel.listaNotas.observe(viewLifecycleOwner){ listaNota ->
            adapter.submitList(listaNota)
        }
        binding.recyclerNotas.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this.adapter = adapter
            setHasFixedSize(true)
        }

        binding.imagePerfil.setOnClickListener {
            BottomSheetFoto(requireContext(), this).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickNota(nota: Nota) {

    }

    override fun onLongClickNota(nota: Nota) {

    }

    override fun cameraSelecionada() {

    }

    override fun galeriaSelecionada() {

    }

    override fun internetSelecionada() {

    }
}