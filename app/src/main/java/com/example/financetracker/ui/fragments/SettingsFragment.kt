package com.example.financetracker.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentSettingsBinding
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel
import com.example.financetracker.viewmodel.UserViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var cashViewModel: CashViewModel
    private lateinit var cardViewModel: CardViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)

        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        val isDarkMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        binding.themeSwitcher.isChecked = isDarkMode
        binding.imageBack.setOnClickListener(){
            findNavController().navigate(R.id.action_settingsFragment_to_profileFragment)
        }

        binding.themeSwitcher.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        binding.imageLogout.setOnClickListener(){
            val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()
            editor?.apply()
            transactionViewModel.deleteTransactions()
            cardViewModel.deleteCards()
            cashViewModel.deleteCashes()
            findNavController().navigate(R.id.action_settingsFragment_to_singInFragment)
        }
        return binding.root
    }

}