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
import com.example.financetracker.databinding.FragmentBillBinding
import com.example.financetracker.ui.adapters.CardAdapter
import com.example.financetracker.ui.adapters.CashAdapter
import com.example.financetracker.ui.adapters.CashTransactionAdapter
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel


class BillFragment : Fragment() {
    private lateinit var binding: FragmentBillBinding
    private lateinit var cardViewModel: CardViewModel
    private lateinit var cashViewModel: CashViewModel
    private lateinit var cardAdapter: CardAdapter
    private lateinit var cashAdapter: CashAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillBinding.inflate(layoutInflater, container, false)
        binding.imageBack.setOnClickListener(){
            findNavController().navigate(R.id.action_billFragment_to_profileFragment)
        }
        binding.imageAddCard.setOnClickListener(){
            findNavController().navigate(R.id.action_billFragment_to_addCardFragment)
        }
        binding.imageAddCash.setOnClickListener(){
            findNavController().navigate(R.id.action_billFragment_to_addCashFragment)
        }

        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        cardAdapter = CardAdapter(cardViewModel.cardsList, requireContext())
        binding.cardList.layoutManager = LinearLayoutManager(context)
        binding.cardList.adapter = cardAdapter

        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        cashAdapter = CashAdapter(cashViewModel.cashList, requireContext())
        binding.cashList.layoutManager = LinearLayoutManager(context)
        binding.cashList.adapter = cashAdapter
        return binding.root
    }

}