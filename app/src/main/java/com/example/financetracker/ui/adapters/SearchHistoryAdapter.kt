package com.example.financetracker.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R

class SearchHistoryAdapter(private var searchHistory: List<String>) :
    RecyclerView.Adapter<SearchHistoryAdapter.SearchHistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_history, parent, false)
        return SearchHistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val currentItem = searchHistory[position]
        holder.textView.text = currentItem
    }

    override fun getItemCount(): Int {
        return searchHistory.size
    }

    fun updateList(newList: List<String>) {
        searchHistory = newList
        notifyDataSetChanged()
    }

    inner class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.historyTextView)
    }
}