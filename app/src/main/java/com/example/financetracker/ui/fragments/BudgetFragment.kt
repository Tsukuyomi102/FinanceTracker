package com.example.financetracker.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentBudgetBinding
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel

class BudgetFragment : Fragment() {
    private lateinit var binding: FragmentBudgetBinding

    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var cardViewModel: CardViewModel
    private lateinit var cashViewModel: CashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBudgetBinding.inflate(layoutInflater, container, false)

        cardViewModel = ViewModelProvider(requireActivity())[CardViewModel::class.java]
        cashViewModel = ViewModelProvider(requireActivity())[CashViewModel::class.java]
        transactionViewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]

        changeCardBalance(cardViewModel)
        changeCashBalance(cashViewModel)
        changeGeneralBalance()

        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)

        return binding.root
    }

    private fun changeCardBalance(cardViewModel: CardViewModel){
        cardViewModel.getCardsLiveData().observe(viewLifecycleOwner, Observer { cards ->
            if (cards.isNotEmpty()) {
                val totalBalance = cardViewModel.getTotalBalance()
                binding.cardBalance.text = "$totalBalance RUB"
            } else {
                binding.cardBalance.text = "haven't cards"
            }
        })
    }

    private fun changeCashBalance(cashViewModel: CashViewModel){
        cashViewModel.getCashesLiveData().observe(viewLifecycleOwner, Observer { cashes ->
            if (cashes.isNotEmpty()) {
                val totalBalance = cashViewModel.getTotalBalance()
                binding.cashBalance.text = "$totalBalance RUB"
            } else {
                binding.cashBalance.text = "haven't cashes"
            }
        })
    }

    private fun changeGeneralBalance() {
        val generalBalance = cashViewModel.getTotalBalance() + cardViewModel.getTotalBalance()
        binding.generalBalance.text = "$generalBalance RUB"
    }
}