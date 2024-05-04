package com.example.financetracker.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.Cash

class CashAdapter(private val cashes: List<Cash>, private val context: Context, private val listener: OnCashClickListener) : RecyclerView.Adapter<CashAdapter.CashViewHolder>(){
    public interface OnCashClickListener{
        fun onCashClick(cash: Cash)
    }

    private val cashList = mutableListOf<Cash>()
    init {
        addCard()
    }

    private fun addCard(){
        cashList.clear()

        for(cash in cashes){
            cashList.add(cash)
        }
    }


    class CashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCash: ImageView = itemView.findViewById(R.id.imageCash)
        val textCashName: TextView = itemView.findViewById(R.id.textCashName)
        val textCashDescription: TextView = itemView.findViewById(R.id.textCashDescription)
        val textCashBalance: TextView = itemView.findViewById(R.id.textCashBalance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cash_item, parent, false)
        return CashAdapter.CashViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cashList.size
    }

    override fun onBindViewHolder(holder: CashViewHolder, position: Int) {
        val cash = cashList[position]
        holder.textCashName.text = cash.cashName
        holder.textCashDescription.text = cash.cashDescription
        holder.textCashBalance.text = "${cash.cashBalance} RUB"

        holder.itemView.setOnClickListener(){
            listener.onCashClick(cash)
        }
    }
}