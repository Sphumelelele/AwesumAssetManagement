package com.awesum.assetmanagement.ui.asset

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.awesum.assetmanagement.R
import com.awesum.assetmanagement.model.Asset

class AssetAdapter(private var assets: List<Asset> = emptyList()) :
    RecyclerView.Adapter<AssetAdapter.AssetViewHolder>() {

    fun submitList(newList: List<Asset>) {
        assets = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_asset, parent, false)
        return AssetViewHolder(view)
    }

    override fun onBindViewHolder(holder: AssetViewHolder, position: Int) {
        holder.bind(assets[position], position)
    }

    override fun getItemCount(): Int = assets.size

    class AssetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvIndex: TextView = itemView.findViewById(R.id.tvIndex)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val badgeCategory: TextView = itemView.findViewById(R.id.badgeCategory)
        private val badgeStatus: TextView = itemView.findViewById(R.id.badgeStatus)

        fun bind(asset: Asset, position: Int) {
            tvIndex.text = String.format("%02d", position + 1)
            tvName.text = asset.name
            badgeCategory.text = asset.category
            badgeStatus.text = asset.status

            val (catBg, catText) = when (asset.category) {
                "Dispatch" -> Pair("#1A3350", "#4DA3FF")
                "Equipment" -> Pair("#2B1F45", "#B18CFF")
                "Vehicle" -> Pair("#3D1B22", "#F26D6D")
                else -> Pair("#1A3350", "#4DA3FF")
            }
            badgeCategory.background.setTint(catBg.toColorInt())
            badgeCategory.setTextColor(catText.toColorInt())

            val (statBg, statText) = if (asset.status == "Out for Service")
                Pair("#3D2A10", "#F2A93B") else Pair("#173B27", "#3ECF7A")
            badgeStatus.background.setTint(statBg.toColorInt())
            badgeStatus.setTextColor(statText.toColorInt())
        }
    }
}
