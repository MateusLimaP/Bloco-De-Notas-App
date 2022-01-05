package com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.mateuslima.blocodenotas.BuildConfig
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.util.UriUtils
import com.mateuslima.blocodenotas.core.util.setOnQueryTextChange
import com.mateuslima.blocodenotas.databinding.FragmentHomeNotesBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota
import com.mateuslima.blocodenotas.feature_notas.domain.repository.NotasPrefsRepository
import com.mateuslima.blocodenotas.feature_notas.presentation.adapter.NotasAdapter
import com.mateuslima.blocodenotas.feature_notas.presentation.ui.selecao_foto.SelecaoFotoFragment
import com.mateuslima.blocodenotas.feature_notas.presentation.util.BottomSheetFoto
import com.mateuslima.blocodenotas.feature_notas.presentation.util.NotasDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

@AndroidEntryPoint
class HomeNotesFragment : Fragment(R.layout.fragment_home_notes), NotasAdapter.NotasAdapterListener,
BottomSheetFoto.BottomSheetFotoListener{

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



    }

    private val launchWritePermission = registerForActivityResult(RequestPermission()){ permitido ->
        if (permitido) BottomSheetFoto(requireContext(), this).show()
        else showDialogPermissaoEscrita()
    }

    private fun showDialogPermissaoEscrita(){
        NotasDialog.permissaoEscritaGaleria(requireContext(), launchWritePermission)
    }

    private val launchCamera = registerForActivityResult(StartActivityForResult()){ response ->
        if (response.resultCode == Activity.RESULT_OK){
            val imagemBitmap = response.data?.extras?.get("data") as Bitmap
            val caminhoUri: Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) caminhoUri = saveImageInQ(imagemBitmap)
            else caminhoUri = saveTheImageLegacyStyle(imagemBitmap)

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

    }

    override fun onLongClickNota(nota: Nota) {

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
        findNavController().navigate(HomeNotesFragmentDirections.actionHomeNotesFragmentToSelecaoFotoFragment())
        // receber dados do fragment selecao fotos
        setFragmentResultListener(SelecaoFotoFragment::class.java.name){ chave, bundle ->
            val imagemUrl = bundle.getString(CHAVE_IMAGEM_URL_SELECIONADA) ?: ""
            viewModel.salvarFotoPerfil(imagemUrl)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveImageInQ(bitmap: Bitmap):Uri {
        val filename = "IMG_Img.jpg"
        var fos: OutputStream? = null
        var imageUri: Uri? = null
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }

        //use application context to get contentResolver
        val contentResolver = requireContext().contentResolver

        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }

        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }

        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        contentResolver.update(imageUri!!, contentValues, null, null)

        return imageUri!!
    }

    fun saveTheImageLegacyStyle(bitmap:Bitmap) : Uri {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, bitmap, "image.jpeg",null)
        return Uri.parse(path)
    }
}