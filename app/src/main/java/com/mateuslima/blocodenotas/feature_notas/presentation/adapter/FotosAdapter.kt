package com.mateuslima.blocodenotas.feature_notas.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mateuslima.blocodenotas.databinding.ContainerFotosBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Foto

class FotosAdapter(
    val listener: FotosAdapterListener
) : PagingDataAdapter<Foto, FotosAdapter.MyViewHolder>(Companion) {

    companion object: DiffUtil.ItemCallback<Foto>(){
        override fun areItemsTheSame(oldItem: Foto, newItem: Foto): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: Foto, newItem: Foto): Boolean {
            return newItem.imagemPequenaUrl == oldItem.imagemPequenaUrl
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerFotosBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val foto = getItem(position)!!
        holder.binding.apply {
            Glide.with(root.context).load(foto.imagemPequenaUrl).into(imageFoto)
        }
    }



    inner class MyViewHolder(val binding: ContainerFotosBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener { listener.onClickFoto(getItem(bindingAdapterPosition)!!) }
        }

    }

    interface FotosAdapterListener{
        fun onClickFoto(foto: Foto)
    }
}