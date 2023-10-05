package com.aajogo.jogo.sure.ui.game.spin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aajogo.jogo.sure.databinding.SpinItemLayoutBinding

class SpinAdapter : RecyclerView.Adapter<SpinViewHolder>() {

    private val resIds = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpinViewHolder {
        val binding =
            SpinItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpinViewHolder(binding)
    }

    override fun getItemCount() = resIds.size

    override fun onBindViewHolder(holder: SpinViewHolder, position: Int) {
        holder.bind(resIds[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(resIds: List<Int>) {
        this.resIds.clear()
        this.resIds.addAll(resIds)
        notifyDataSetChanged()
    }
}