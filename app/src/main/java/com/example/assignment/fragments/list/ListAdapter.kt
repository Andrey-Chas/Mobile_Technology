package com.example.assignment.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.model.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListAdapter: RecyclerView.Adapter<ListAdapter.MViewHolder>(), Filterable {

    private var userList = arrayListOf<User>()
    private var userListFiltered: List<User> = arrayListOf()

    class MViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.itemView.findViewById<TextView>(R.id.firstName_txt).text = currentItem.firstName
        holder.itemView.findViewById<TextView>(R.id.lastName_txt).text = currentItem.lastName

        holder.itemView.findViewById<ImageButton>(R.id.btnEdit_txt).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.itemView.findViewById<ConstraintLayout>(R.id.rowLayout).setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>) {
        userList.clear()
        userList.addAll(user)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        var filter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var filterResults = FilterResults()
                if (p0 == null || p0.isEmpty()) {
                    filterResults.values = userListFiltered
                    filterResults.count = userListFiltered.size
                } else {
                    var searchChar = p0.toString().lowercase()

                    var filteredResults = ArrayList<User>()

                    for (user in userListFiltered) {
                        if(user.firstName.lowercase().contains(searchChar)) {
                            filteredResults.add(user)
                        }
                    }

                    filterResults.values = filteredResults
                    filterResults.count = filteredResults.size
                }

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                userList.clear()
                userList.addAll(p1!!.values as ArrayList<User>)
                notifyDataSetChanged()
            }

        }

        return filter
    }
}