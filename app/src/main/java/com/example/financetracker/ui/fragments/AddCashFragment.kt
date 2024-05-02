package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.data.Cash
import com.example.financetracker.databinding.FragmentAddCashBinding
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel

class AddCashFragment : Fragment() {
    private lateinit var binding: FragmentAddCashBinding
    private lateinit var viewModel: CashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCashBinding.inflate(layoutInflater, container, false)
        binding.imageBack.setOnClickListener(){
            findNavController().navigate(R.id.action_addCashFragment_to_billFragment)
        }

        viewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        binding.buttonAddCash.setOnClickListener(){
            val cash = Cash(
                cashName = binding.editCashName.text.toString(),
                cashDescription = binding.editCashDescription.text.toString(),
                cashBalance = binding.editCashBalance.text.toString().toInt()
            )
            viewModel.addCash(cash)
            Toast.makeText(context, "Ваши наличные успешно добавлены!", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}