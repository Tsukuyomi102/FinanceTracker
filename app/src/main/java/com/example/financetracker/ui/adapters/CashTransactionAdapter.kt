package com.example.financetracker.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.financetracker.R
import com.example.financetracker.data.Transaction

class CashTransactionAdapter(private val transactions: List<Transaction>, private val context: Context, private val flagForAll: Boolean, private val listener: CashTransactionAdapter.OnCashTransactionClickListener) : RecyclerView.Adapter<CashTransactionAdapter.TransactionViewHolder>() {
    private var cashTransactions = mutableListOf<Transaction>()

    interface OnCashTransactionClickListener{
        fun onCashTransactionClick(transaction: Transaction)
    }
    init {
        splitTransactions()
    }

    private fun splitTransactions() {
        cashTransactions.clear()

        for (transaction in transactions) {
            if (!transaction.isCreditCard) {
                cashTransactions.add(transaction)
            }
        }
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageCategory: ImageView = itemView.findViewById(R.id.imageCategory)
        val textDate: TextView = itemView.findViewById(R.id.textDate)
        val textName: TextView = itemView.findViewById(R.id.textName)
        val textAmount: TextView = itemView.findViewById(R.id.textAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (!(flagForAll)) {
            if (cashTransactions.size >= 4)
                4
            else
                cashTransactions.size
        } else {
            cashTransactions.size
        }
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val reversedPosition = cashTransactions.size - 1 - position
        val transaction = cashTransactions[reversedPosition]
        holder.textName.text = transaction.transactionName
        holder.textAmount.text = "${transaction.amount} RUB"
        holder.textDate.text = transaction.date
        if (transaction.isIncome) {
            holder.textAmount.setTextColor(ContextCompat.getColor(context, R.color.income))
        } else {
            holder.textAmount.setTextColor(ContextCompat.getColor(context, R.color.outcome))
        }

        val iconResId = when (transaction.category) {
            "imageCar" -> R.drawable.icon_car_circle
            "imageFood" -> R.drawable.icon_food_circle
            "imageGift" -> R.drawable.icon_gift_circle
            "imageHealth" -> R.drawable.icon_health_circle
            "imageClothes" -> R.drawable.icon_clothes_circle
            "imageDonation" -> R.drawable.icon_donation_circle
            "imageBeauty" -> R.drawable.icon_beauty_cicle
            "imageHome" -> R.drawable.icon_home_circle
            "imageMoney" -> R.drawable.icon_money_circle
            "imageBusiness" -> R.drawable.icon_business_circle
            "imageInvestment" -> R.drawable.icon_investment_circle
            else -> R.drawable.avatar_svgrepo_com
        }
        holder.imageCategory.setImageResource(iconResId)
        holder.itemView.setOnClickListener(){
            listener.onCashTransactionClick(transaction)
        }
    }

    fun filterList(filteredTransactions: List<Transaction>) {
        cashTransactions = filteredTransactions.toMutableList()
        notifyDataSetChanged()
    }


}