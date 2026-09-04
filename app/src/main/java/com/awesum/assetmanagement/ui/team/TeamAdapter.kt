package com.awesum.assetmanagement.ui.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.awesum.assetmanagement.R
import com.awesum.assetmanagement.model.TeamMember

class TeamAdapter(private var members: List<TeamMember> = emptyList()) :
    RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    fun submitList(newList: List<TeamMember>) {
        members = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team_member, parent, false)
        return TeamViewHolder(view)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int = members.size

    class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvInitials: TextView = itemView.findViewById(R.id.tvInitials)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvRole: TextView = itemView.findViewById(R.id.tvRole)

        fun bind(member: TeamMember) {
            tvName.text = member.full_name
            tvRole.text = member.role
            tvInitials.text = member.full_name
                .split(" ")
                .filter { it.isNotBlank() }
                .take(2)
                .joinToString("") { it.first().uppercaseChar().toString() }
        }
    }
}
