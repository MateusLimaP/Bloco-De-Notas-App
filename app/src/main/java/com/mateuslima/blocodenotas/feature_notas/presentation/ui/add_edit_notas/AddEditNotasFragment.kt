package com.mateuslima.blocodenotas.feature_notas.presentation.ui.add_edit_notas

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.util.ImageUtil
import com.mateuslima.blocodenotas.core.util.UriUtils
import com.mateuslima.blocodenotas.databinding.FragmentAddEditNotasBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.CoresAdapter
import com.mateuslima.blocodenotas.feature_notas.presentation.ui.selecao_foto.SelecaoFotoFragment
import com.mateuslima.blocodenotas.feature_notas.presentation.util.BottomSheetFoto
import com.mateuslima.blocodenotas.feature_notas.presentation.util.NotasDialog
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class AddEditNotasFragment : Fragment(R.layout.fragment_add_edit_notas), CoresAdapter.CoresAdapterListener,
BottomSheetFoto.BottomSheetFotoListener{

    private var _binding: FragmentAddEditNotasBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddEditNotasViewModel by viewModels()
    companion object{const val CHAVE_IMAGEM_URL_NOTAS = "add_edit_imagemnota"}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEditNotasBinding.bind(view)
        binding.root.setBackgroundColor(Color.parseColor(viewModel.corNota))

        val corAdapter = CoresAdapter(this)
        //corAdapter.setSizeViewColor(40)
        corAdapter.setColorSelected(viewModel.corNota)
        binding.recyclerCor.apply {
            layoutManager = LinearLayoutManager(requireContext()).apply { orientation = LinearLayoutManager.HORIZONTAL }
            adapter = corAdapter
            setHasFixedSize(true)
        }

        viewModel.imagemNotaObservable.observe(viewLifecycleOwner){ url ->
            val isImageFile = File(url).isFile
            if (isImageFile) binding.imageDraweeNota.setImageURI(Uri.fromFile(File(url)).toString())
            else binding.imageDraweeNota.setImageURI(url)

        }

        binding.editTitulo.setText(viewModel.tituloNota)
        binding.editDescricao.setText(viewModel.descricaoNota)

        binding.imageDraweeNota.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                launchWritePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            else BottomSheetFoto(requireContext(), this).show()  // scope storage
        }

        binding.fabSalvar.setOnClickListener {
            viewModel.salvarNota(Nota(
                titulo = binding.editTitulo.text.toString(),
                descricao = binding.editDescricao.text.toString(),
                imagemUrl = viewModel.imagemNota,
                corHex = viewModel.corNota,
                data = ""
            ))
            findNavController().popBackStack()
        }





    }

    private val launchWritePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ permitido ->
        if (permitido) BottomSheetFoto(requireContext(), this).show()
        else showDialogPermissaoEscrita()
    }

    private fun showDialogPermissaoEscrita(){
        NotasDialog.permissaoEscritaGaleria(requireContext(), launchWritePermission)
    }

    private val launchGaleria = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ response ->
        if (response.resultCode == Activity.RESULT_OK){
            val caminhoUri = response.data?.data

            val caminhoCompleto = UriUtils.getPathFromUri(requireContext(), caminhoUri)
            viewModel.setImagemNotaUrl(caminhoCompleto)
        }
    }

    private val launchCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ response ->
        if (response.resultCode == Activity.RESULT_OK){
            val imagemBitmap = response.data?.extras?.get("data") as Bitmap
            val caminhoUri: Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) caminhoUri = ImageUtil.saveImageInQ(requireContext(), imagemBitmap)
            else caminhoUri = ImageUtil.saveTheImageLegacyStyle(requireContext(), imagemBitmap)

            val caminhoCompleto = UriUtils.getPathFromUri(requireContext(), caminhoUri)
            viewModel.setImagemNotaUrl(caminhoCompleto)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickCorSelecionada(corHex: String) {
        binding.root.setBackgroundColor(Color.parseColor(corHex))
        viewModel.corNota = corHex
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
        findNavController().navigate(AddEditNotasFragmentDirections
            .actionAddEditNotasFragmentToSelecaoFotoFragment(AddEditNotasFragment::class.java.name))

        setFragmentResultListener(SelecaoFotoFragment::class.java.name){chave, bundle ->
            val imagemUrl = bundle.getString(CHAVE_IMAGEM_URL_NOTAS) ?: ""
            viewModel.setImagemNotaUrl(imagemUrl)

        }
    }
}