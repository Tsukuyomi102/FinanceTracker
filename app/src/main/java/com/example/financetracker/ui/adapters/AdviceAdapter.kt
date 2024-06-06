package com.example.financetracker.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.Advice


class AdviceAdapter(private val advices: List<Advice>, private val context: Context) : RecyclerView.Adapter<AdviceAdapter.AdviceViewHolder>() {
    private val adviceList = mutableListOf<Advice>()

    init {
        addAdvice()
    }

    private fun addAdvice(){
        adviceList.clear()

        for(advice in advices){
            adviceList.add(advice)
        }
    }

    class AdviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val adviceName: TextView = itemView.findViewById(R.id.adviceName)
        val adviceDescription: TextView = itemView.findViewById(R.id.adviceDescription)
        val adviceButton: TextView = itemView.findViewById(R.id.links)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.advice_item, parent, false)
        return AdviceAdapter.AdviceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return adviceList.size
    }

    override fun onBindViewHolder(holder: AdviceViewHolder, position: Int) {
        val advice = adviceList[position]
        holder.adviceName.text = advice.adviceName
        holder.adviceDescription.text = advice.adviceDescription
        holder.adviceButton.setOnClickListener(){
            val url = "https://www.investopedia.com/beginners-guide-to-investing-4689655"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            holder.itemView.context.startActivity(intent)
        }
    }
}