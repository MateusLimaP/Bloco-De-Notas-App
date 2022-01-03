package com.mateuslima.blocodenotas.feature_notas.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mateuslima.blocodenotas.databinding.ContainerNotasBinding
import com.mateuslima.blocodenotas.feature_notas.domain.model.Nota

class NotasAdapter(
    private val listener: NotasAdapterListener
) : ListAdapter<Nota, NotasAdapter.MyViewHolder>(Companion) {

    companion object: DiffUtil.ItemCallback<Nota>(){
        override fun areItemsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Nota, newItem: Nota): Boolean {
            return oldItem.data == newItem.data
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerNotasBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val nota = getItem(position)
        holder.binding.apply {
            textData.text = nota.data
            textDescricao.text = nota.descricao
            textTitulo.text = nota.titulo

            Glide.with(root.context).load(nota.imagemUrl).into(imageNota)

            if (nota.imagemUrl.isEmpty()) cardImageNota.visibility = View.GONE
            else cardImageNota.visibility = View.VISIBLE


        }
    }

    inner class MyViewHolder(val binding: ContainerNotasBinding)
        : RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener { listener.onClickNota(getItem(bindingAdapterPosition)) }
                binding.root.setOnLongClickListener {
                    listener.onLongClickNota(getItem(bindingAdapterPosition))
                    true }
            }
    }

    interface NotasAdapterListener{
        fun onClickNota(nota: Nota)
        fun onLongClickNota(nota: Nota)
    }
}