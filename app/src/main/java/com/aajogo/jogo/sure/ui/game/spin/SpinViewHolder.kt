package com.aajogo.jogo.sure.ui.game.spin

import androidx.recyclerview.widget.RecyclerView
import com.aajogo.jogo.sure.databinding.SpinItemLayoutBinding

class SpinViewHolder(
    private val binding: SpinItemLayoutBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(resId: Int) {
        binding.itemImageView.setBackgroundResource(resId)
    }
}