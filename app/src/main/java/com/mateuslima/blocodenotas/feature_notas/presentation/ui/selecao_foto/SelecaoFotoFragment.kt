package com.mateuslima.blocodenotas.feature_notas.presentation.ui.selecao_foto

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.util.setOnQueryTexSubmit
import com.mateuslima.blocodenotas.databinding.FragmentSelecaoFotoBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Foto
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.FotosAdapter
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.NotasLoadStateAdapter
import com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes.HomeNotesFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelecaoFotoFragment : Fragment(R.layout.fragment_selecao_foto), FotosAdapter.FotosAdapterListener {

    private var _binding: FragmentSelecaoFotoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SelecaoFotoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelecaoFotoBinding.bind(view)

        val adapter = FotosAdapter(this)
        viewModel.listaFoto.observe(viewLifecycleOwner){ listaFoto ->
            viewLifecycleOwner.lifecycleScope.launch { adapter.submitData(listaFoto) }
        }

        binding.searchFoto.setOnQueryTexSubmit { search -> viewModel.pesquisa.value = search }
        binding.recyclerFotos.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter.withLoadStateHeaderAndFooter(
                footer = NotasLoadStateAdapter{ adapter.retry() },
                header = NotasLoadStateAdapter{ adapter.retry() }
            )
            setHasFixedSize(true)
        }

        adapter.addLoadStateListener { loadState ->
            binding.progressLayout.isVisible = loadState.refresh is LoadState.Loading
            binding.textLoadError.isVisible = loadState.refresh is LoadState.Error
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickFoto(foto: Foto) {
        // enviar dados para home notes fragment
        setFragmentResult(this::class.java.name,
            bundleOf(HomeNotesFragment.CHAVE_IMAGEM_URL_SELECIONADA to foto.imagemPequenaUrl))
        findNavController().popBackStack() // retornar para pagina anterior

    }
}