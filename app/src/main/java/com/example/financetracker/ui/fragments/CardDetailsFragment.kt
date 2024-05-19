package com.example.financetracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.FragmentCardDetailsBinding
import com.example.financetracker.ui.adapters.CardTransactionAdapter
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.TransactionViewModel

class CardDetailsFragment : Fragment(), CardTransactionAdapter.OnCardTransactionClickListener {
    private lateinit var binding: FragmentCardDetailsBinding
    private lateinit var cardViewModel: CardViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardDetailsBinding.inflate(inflater, container, false)
        binding.imageBack.setOnClickListener() {
            findNavController().navigate(R.id.action_cardDetailsFragment_to_billFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)

        val selectedCard = cardViewModel.getSelectedCard()
        selectedCard?.let {
            binding.textCardName.text = it.cardName
            binding.textCardNumber.text = "**** **** **** ${it.cardNumber % 10000}"
            binding.textCardBalance.text = "${it.cardBalance} RUB"
            binding.textCardDate.text = "${it.month}/${it.year}"

            val transactionsForSelectedCard = transactionViewModel.getTransactionsForSelectedCard(selectedCard)
            val transactionAdapter = CardTransactionAdapter(transactionsForSelectedCard, requireContext(), true, this)
            binding.recyclerCardTransactions.layoutManager = LinearLayoutManager(context)
            binding.recyclerCardTransactions.adapter = transactionAdapter
        }
    }

    override fun onCardTransactionClick(transaction: Transaction) {
        transactionViewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_cardDetailsFragment_to_transactionDetailsFragment)
    }
}
