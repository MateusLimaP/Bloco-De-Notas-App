package com.mateuslima.blocodenotas.feature_notas.presentation.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mateuslima.blocodenotas.R
import com.mateuslima.blocodenotas.core.util.dp
import com.mateuslima.blocodenotas.databinding.ContainerCoresBinding

class CoresAdapter(
    val listener: CoresAdapterListener
) : RecyclerView.Adapter<CoresAdapter.MyViewHolder>() {

    private val coresList = carregarCores()
    private var sizeViewColor = 40.dp
    private var corSelected = ""

    fun setSizeViewColor(size: Int){
        sizeViewColor = size.dp
    }

    fun setColorSelected(cor: String){
        corSelected = cor
    }

    private fun carregarCores() : List<CorWrapper>{
        val corList = listOf(
            CorWrapper("#2196F3"), // azul
            CorWrapper("#FFEB3B"), // amarelo
            CorWrapper("#9C27B0"), // roxo
            CorWrapper("#E91E63"), // rosa
            CorWrapper("#f17e14"), // laranja
            CorWrapper("#00BCD4"), // azul marinho
            CorWrapper("#D82618"), // vermelho
            CorWrapper("#1a212f"), // semi dark
        )

        return corList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ContainerCoresBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val corWrapper = coresList[position]
        // alterar cor principal e borda da view color
        val drawable = holder.binding.viewColor.background.mutate() as GradientDrawable
        drawable.setStroke(3, Color.WHITE)
        drawable.color = ColorStateList.valueOf(Color.parseColor(corWrapper.corHex))

        // alterar tamanho do view color
       /* val params = holder.binding.root.layoutParams
        params.height = sizeViewColor
        holder.binding.viewColor.layoutParams = params*/

        // mostrar item selecionado
        corWrapper.isSelected = corWrapper.corHex == corSelected
        holder.binding.viewSelecao.isVisible = corWrapper.isSelected



    }

    override fun getItemCount(): Int {
        return coresList.size
    }

    inner class MyViewHolder(val binding: ContainerCoresBinding)
        : RecyclerView.ViewHolder(binding.root){
            init {
                binding.viewColor.setOnClickListener {
                    val corWrapper = coresList[bindingAdapterPosition]
                    corWrapper.isSelected = !corWrapper.isSelected
                    if (corWrapper.isSelected) corSelected = corWrapper.corHex

                    listener.onClickCorSelecionada(corWrapper.corHex)
                    notifyDataSetChanged()
                }
            }
        }

    data class CorWrapper(val corHex: String, var isSelected: Boolean = false)

    interface CoresAdapterListener{
        fun onClickCorSelecionada(corHex: String)
    }


}