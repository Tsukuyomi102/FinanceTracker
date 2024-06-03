package com.example.financetracker.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.data.Card
import com.example.financetracker.databinding.FragmentAddCardBinding
import com.example.financetracker.viewmodel.CardViewModel

class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    private lateinit var viewModel: CardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCardBinding.inflate(layoutInflater, container, false)

        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "").toString()

        binding.imageBack.setOnClickListener {
            findNavController().navigate(R.id.action_addCardFragment_to_billFragment)
        }

        viewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)

        binding.buttonAddCard.setOnClickListener {
            val cardName = binding.editCardName.text.toString()
            val cardBalanceStr = binding.editCardBalance.text.toString()
            val cardNumberStr = binding.editCardNumber.text.toString()
            val monthStr = binding.editCardMonth.text.toString()
            val yearStr = binding.editCardYear.text.toString()

            if (cardName.isNotEmpty() && cardBalanceStr.isNotEmpty() && cardNumberStr.isNotEmpty() &&
                monthStr.isNotEmpty() && yearStr.isNotEmpty()
            ) {
                val cardBalance = cardBalanceStr.toIntOrNull()
                val cardNumber = cardNumberStr.toLongOrNull()
                val month = monthStr.toIntOrNull()
                val year = yearStr.toIntOrNull()

                if (cardBalance != null && cardNumber != null && month != null && year != null) {
                    val card = Card(
                        cardName = cardName,
                        cardBalance = cardBalance,
                        cardNumber = cardNumber,
                        month = month,
                        year = year,
                    )
                    viewModel.addCardByEmail(email, card)
                    Toast.makeText(context, getString(R.string.cardAdded), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, getString(R.string.cardError), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, getString(R.string.allInformation), Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}