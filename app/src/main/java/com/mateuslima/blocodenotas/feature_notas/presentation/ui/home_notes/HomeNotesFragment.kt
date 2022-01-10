package com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.util.ImageUtil
import com.mateuslima.blocodenotas.core.util.UriUtils
import com.mateuslima.blocodenotas.core.util.setOnQueryTextChange
import com.mateuslima.blocodenotas.databinding.FragmentHomeNotesBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.NotasAdapter
import com.mateuslima.blocodenotas.feature_notas.presentation.ui.selecao_foto.SelecaoFotoFragment
import com.mateuslima.blocodenotas.feature_notas.presentation.util.BottomSheetConfigNota
import com.mateuslima.blocodenotas.feature_notas.presentation.util.BottomSheetFoto
import com.mateuslima.blocodenotas.feature_notas.presentation.util.NotasDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class HomeNotesFragment : Fragment(R.layout.fragment_home_notes), NotasAdapter.NotasAdapterListener,
BottomSheetFoto.BottomSheetFotoListener, BottomSheetConfigNota.BottomSheetConfigNotaListener{

    private var _binding: FragmentHomeNotesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeNotesViewModel by viewModels()
    companion object{ const val CHAVE_IMAGEM_URL_SELECIONADA = "imagemurl"}

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
                NotasPrefsRepository.OrganizarNota.TITULO -> binding.radioTitulo.isChecked = true
                NotasPrefsRepository.OrganizarNota.COR -> binding.radioCor.isChecked = true
                NotasPrefsRepository.OrganizarNota.DATA -> binding.radioData.isChecked = true
            }
        }

        viewModel.fotoPerfil.observe(viewLifecycleOwner){url ->
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(binding.imagePerfil)
        }

        binding.searchview.setOnQueryTextChange { search -> viewModel.pesquisa.value = search }

        val adapter = NotasAdapter(this)
        viewModel.listaNotas.observe(viewLifecycleOwner){ listaNota ->
            listaNota.forEach { nota -> println("nota = ${nota.titulo}") }
            adapter.submitList(listaNota)
        }
        binding.recyclerNotas.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            this.adapter = adapter
        }


        binding.imagePerfil.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                launchWritePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            else BottomSheetFoto(requireContext(), this).show()  // scope storage
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(HomeNotesFragmentDirections
                .actionHomeNotesFragmentToAddEditNotasFragment(null))
        }



    }

    private val launchWritePermission = registerForActivityResult(RequestPermission()){ permitido ->
        if (permitido) BottomSheetFoto(requireContext(), this).show()
        else showDialogPermissaoEscrita()
    }

    private fun showDialogPermissaoEscrita(){
        NotasDialog.permissaoEscrita(requireContext(), launchWritePermission)
    }

    private val launchCamera = registerForActivityResult(StartActivityForResult()){ response ->
        if (response.resultCode == Activity.RESULT_OK){
            val imagemBitmap = response.data?.extras?.get("data") as Bitmap
            val caminhoUri: Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) caminhoUri = ImageUtil.saveImageInQ(requireContext(), imagemBitmap)
            else caminhoUri = ImageUtil.saveTheImageLegacyStyle(requireContext(), imagemBitmap)

            val caminhoCompleto = UriUtils.getPathFromUri(requireContext(), caminhoUri)
            viewModel.salvarFotoPerfil(caminhoCompleto)
        }
    }

    private val launchGaleria = registerForActivityResult(StartActivityForResult()){response ->
        if (response.resultCode == Activity.RESULT_OK){
            val caminhoUri = response.data?.data

            val caminhoCompleto = UriUtils.getPathFromUri(requireContext(), caminhoUri)
            viewModel.salvarFotoPerfil(caminhoCompleto)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickNota(nota: Nota) {
        findNavController().navigate(HomeNotesFragmentDirections
            .actionHomeNotesFragmentToAddEditNotasFragment(nota))
    }

    override fun onLongClickNota(nota: Nota) {
        BottomSheetConfigNota(requireContext(), nota,this).show()
    }

    override fun cameraSelecionada() {
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launchCamera.launch(intentCamera)
    }

    override fun galeriaSelecionada() {
        val intentGaleria = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launchGaleria.launch(intentGaleria)

    }


    override fun internetSelecionada() {
        findNavController().navigate(HomeNotesFragmentDirections
            .actionHomeNotesFragmentToSelecaoFotoFragment(HomeNotesFragment::class.java.name))
        // receber dados do fragment selecao fotos
        setFragmentResultListener(SelecaoFotoFragment::class.java.name){ chave, bundle ->
            val imagemUrl = bundle.getString(CHAVE_IMAGEM_URL_SELECIONADA) ?: ""
            viewModel.salvarFotoPerfil(imagemUrl)
        }
    }

    override fun atualizarCor(corHex: String, nota: Nota) {
        viewModel.atualizarCorNota(corHex, nota)
    }

    override fun compartilharNota(nota: Nota) {
        val intentCompartilhar = Intent(Intent.ACTION_SEND)
        intentCompartilhar.setType("text/plain")
        intentCompartilhar.putExtra(Intent.EXTRA_SUBJECT, nota.titulo)
        intentCompartilhar.putExtra(Intent.EXTRA_TEXT, nota.descricao)
        startActivity(Intent.createChooser(intentCompartilhar, getString(R.string.compartilhar)))
    }

    override fun deletarNota(nota: Nota) {
        viewModel.deletarNota(nota)
    }

    override fun copiarNota(nota: Nota) {
        viewModel.copiarNota(nota)
    }
}