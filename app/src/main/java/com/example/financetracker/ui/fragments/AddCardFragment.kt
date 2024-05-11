package com.example.financetracker.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.data.Card
import com.example.financetracker.databinding.FragmentAddCardBinding
import com.example.financetracker.viewmodel.CardViewModel


class AddCardFragment : Fragment() {
    private lateinit var binding: FragmentAddCardBinding
    private lateinit var viewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCardBinding.inflate(layoutInflater, container, false)
        binding.imageBack.setOnClickListener(){
            findNavController().navigate(R.id.action_addCardFragment_to_billFragment)
        }

        viewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        binding.buttonAddCard.setOnClickListener(){
            val card = Card(
                cardName = binding.editCardName.text.toString(),
                cardBalance = binding.editCardBalance.text.toString().toInt(),
                cardNumber = binding.editCardNumber.text.toString().toLong(),
                month = binding.editCardMonth.text.toString().toInt(),
                year = binding.editCardYear.text.toString().toInt(),
            )

            val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
            val email = sharedPreferences?.getString("email", "")

            if(!email.isNullOrEmpty()){
                viewModel.addCardByEmail(email, card)
                Toast.makeText(context, "Ваша карта успешно добавлена!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Ошибка при добавлении карты. Электронная почта пользователя не найдена.", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }
}