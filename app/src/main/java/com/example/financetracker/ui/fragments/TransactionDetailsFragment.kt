package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentTransactionDetailsBinding
import com.example.financetracker.ui.adapters.CardTransactionAdapter
import com.example.financetracker.viewmodel.TransactionViewModel

class TransactionDetailsFragment : Fragment() {
    private lateinit var binding : FragmentTransactionDetailsBinding
    private lateinit var transactionViewModel: TransactionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactionDetailsBinding.inflate(layoutInflater, container, false)
        binding.imageBack.setOnClickListener() {
            findNavController().navigate(R.id.action_transactionDetailsFragment_to_budgetFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)

        val selectedTransaction = transactionViewModel.getSelectedTransaction()

        selectedTransaction?.let {
            binding.transactionBalance.text = "${it.amount} RUB"
            binding.transactionName.text = it.transactionName
            binding.transactionDate.text = it.date
            if (it.isCreditCard){
                binding.cardNumberOrCashName.text = "**** **** **** ${it.cardNumber % 10000}"
            } else {
                binding.cardNumberOrCashName.text = it.cashName
            }
            val iconResId = when (it.category) {
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

            binding.imageCategory.setImageResource(iconResId)
        }
    }
}