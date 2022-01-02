package com.mateuslima.blocodenotas.feature_notas.presentation.ui.home_notes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.databinding.FragmentHomeNotesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeNotesFragment : Fragment(R.layout.fragment_home_notes) {

    private var _binding: FragmentHomeNotesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeNotesBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}