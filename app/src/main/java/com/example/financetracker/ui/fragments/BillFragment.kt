package com.example.financetracker.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.data.Card
import com.example.financetracker.data.Cash
import com.example.financetracker.databinding.FragmentBillBinding
import com.example.financetracker.ui.adapters.CardAdapter
import com.example.financetracker.ui.adapters.CashAdapter
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel


class BillFragment : Fragment(), CardAdapter.OnCardClickListener, CashAdapter.OnCashClickListener {
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

        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "")

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

        if(!email.isNullOrEmpty()){
            cardViewModel.getCardsByEmail(email)
        }

        cardViewModel.getCardsLiveData().observe(viewLifecycleOwner) { cards ->
            cards?.let {
                cardAdapter = CardAdapter(cards, requireContext(), this)
                binding.cardList.layoutManager = LinearLayoutManager(context)
                binding.cardList.adapter = cardAdapter
            }
        }

        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)

        if(!email.isNullOrEmpty()){
            cashViewModel.getCashByEmail(email)
        }

        cashViewModel.getCashesLiveData().observe(viewLifecycleOwner) {cashes ->
            cashes?.let {
                cashAdapter = CashAdapter(cashViewModel.cashList, requireContext(), this)
                binding.cashList.layoutManager = LinearLayoutManager(context)
                binding.cashList.adapter = cashAdapter
            }
        }
        cashAdapter = CashAdapter(cashViewModel.cashList, requireContext(), this)
        binding.cashList.layoutManager = LinearLayoutManager(context)
        binding.cashList.adapter = cashAdapter
        return binding.root
    }

    override fun onCardClick(card: Card) {
        cardViewModel.setSelectedCard(card)
        findNavController().navigate(R.id.action_billFragment_to_cardDetailsFragment)
    }

    override fun onCashClick(cash: Cash) {
        cashViewModel.setSelectedCash(cash)
        findNavController().navigate(R.id.action_billFragment_to_cashDetailsFragment)
    }
}