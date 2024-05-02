package com.example.financetracker.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.Card
import com.example.financetracker.data.Transaction

class CardAdapter(private val cards: List<Card>, private val context: Context) : RecyclerView.Adapter<CardAdapter.CardViewHolder>(){
    private val cardList = mutableListOf<Card>()
    init {
        addCard()
    }

    private fun addCard(){
        cardList.clear()

        for(card in cards){
            cardList.add(card)
        }
    }


    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCard: ImageView = itemView.findViewById(R.id.imageCard)
        val textCardName: TextView = itemView.findViewById(R.id.textCardName)
        val textCardNumber: TextView = itemView.findViewById(R.id.textCardNumber)
        val textCardBalance: TextView = itemView.findViewById(R.id.textCardBalance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return CardAdapter.CardViewHolder(view)
    }

    override fun getItemCount(): Int {
       return cardList.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
       val card = cardList[position]
        holder.textCardName.text = card.cardName
        holder.textCardNumber.text = "****${card.cardNumber.toString().substring(12)}"
        holder.textCardBalance.text = "${card.cardBalance} RUB"
    }
}