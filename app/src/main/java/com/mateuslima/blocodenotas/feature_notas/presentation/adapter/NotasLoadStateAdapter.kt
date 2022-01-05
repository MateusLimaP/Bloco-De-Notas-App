package com.mateuslima.blocodenotas.feature_notas.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mateuslima.blocodenotas.databinding.NotasLoadStateBinding

class NotasLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<NotasLoadStateAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NotasLoadStateBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, loadState: LoadState) {
        holder.binding.apply {
            progressLayout.isVisible = loadState is LoadState.Loading
            layoutError.isVisible = loadState !is LoadState.Loading
        }
    }



    inner class MyViewHolder(val binding: NotasLoadStateBinding):
        RecyclerView.ViewHolder(binding.root){
        init {
            binding.buttonRetry.setOnClickListener { retry.invoke() }
        }
    }
}