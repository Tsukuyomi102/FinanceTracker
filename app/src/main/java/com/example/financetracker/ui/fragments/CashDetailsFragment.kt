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
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.FragmentCashDetailsBinding
import com.example.financetracker.ui.adapters.CardTransactionAdapter
import com.example.financetracker.ui.adapters.CashTransactionAdapter
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel

class CashDetailsFragment : Fragment(), CashTransactionAdapter.OnCashTransactionClickListener {
    private lateinit var binding: FragmentCashDetailsBinding

    private lateinit var cashViewModel: CashViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCashDetailsBinding.inflate(layoutInflater, container, false)
        binding.imageBack.setOnClickListener() {
            findNavController().navigate(R.id.action_cashDetailsFragment_to_billFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)

        val selectedCash = cashViewModel.getSelectedCash()
        selectedCash?.let {
            binding.textCashName.text = it.cashName
            binding.textCashDescription.text = it.cashDescription
            binding.textCashBalance.text = it.cashBalance.toString()

            val transactionsForSelectedCash = transactionViewModel.getTransactionsForSelectedCash(selectedCash)
            val transactionAdapter = CashTransactionAdapter(transactionsForSelectedCash, requireContext(), true, this)
            binding.recyclerCashTransactions.layoutManager = LinearLayoutManager(context)
            binding.recyclerCashTransactions.adapter = transactionAdapter
        }
    }

    override fun onCashTransactionClick(transaction: Transaction) {
        transactionViewModel.setSelectedTransaction(transaction)
        findNavController().navigate(R.id.action_cashDetailsFragment_to_transactionDetailsFragment)
    }
}