package com.mateuslima.blocodenotas.feature_notas.presentation.ui.selecao_foto

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.util.setOnQueryTexSubmit
import com.mateuslima.blocodenotas.databinding.FragmentSelecaoFotoBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Foto
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.FotosAdapter
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
            this.adapter = adapter
            setHasFixedSize(true)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickFoto(foto: Foto) {
        Toast.makeText(requireContext(), foto.nomeUsuario, Toast.LENGTH_SHORT).show()
    }
}