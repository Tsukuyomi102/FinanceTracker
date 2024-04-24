package com.example.financetracker.ui.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableRow
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.FragmentAddIncomeBinding
import com.example.financetracker.viewmodel.TransactionViewModel
import java.util.Date

class AddIncomeFragment : Fragment() {
    private lateinit var binding: FragmentAddIncomeBinding
    private lateinit var viewModel: TransactionViewModel
    var flag: Boolean = true
    private var category: String = ""
    private var buttonsList: MutableList<ImageButton> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)

        binding.textExpense.setOnClickListener() {
            findNavController().navigate(R.id.action_addIncomeFragment_to_addExpenseFragment)
        }

        binding.btnCar.setOnClickListener() {
            changeColor(binding.btnCar, binding.btnNal)
            flag = true
        }

        binding.btnNal.setOnClickListener() {
            changeColor(binding.btnNal, binding.btnCar)
            flag = false
        }


        for (i in 0 until binding.constraintLayout4.childCount){
            val tableRow: TableRow = binding.constraintLayout4.getChildAt(i) as TableRow
            for(j in 0 until tableRow.childCount) {
                val ImageButton: ImageButton = tableRow.getChildAt(j) as ImageButton
                buttonsList.add(ImageButton)
                ImageButton.setOnClickListener(){
                    changeButtonBackground(ImageButton, buttonsList)
                }
            }
        }

        binding.btnAcs.setOnClickListener(){
            var transaction = Transaction(
                isIncome = true,
                isCreditCard = flag,
                transactionName = binding.editTransaction.text.toString(),
                category = category,
                amount = binding.editTextNumber2.text.toString().toInt()
            )
            viewModel.addTransaction(transaction)
            Toast.makeText(context, "Ваша транзакция успешно добавлена!", Toast.LENGTH_SHORT).show()
        }
        viewModel.transactionsLiveData.observe(viewLifecycleOwner) { transactions ->
            transactions.forEach {
                Log.d(
                    "Transaction Data",
                    "Transaction Name: ${it.transactionName}, Amount: ${it.amount}, Category: ${it.category}, isIncome: ${it.isIncome}, isCreditCard: ${it.isCreditCard}"
                )
            }
        }
        return binding.root
    }

    private fun changeColor(firstButton: Button, secondButton: Button) {
        firstButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.buttonColor))
        firstButton.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.white))
        secondButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.white))
        secondButton.setTextColor(ContextCompat.getColor(requireContext(),
            R.color.hintColor2))
    }

    private fun changeButtonBackground(clickedButton: ImageButton, buttonsList: List<ImageButton>) {
        for (button in buttonsList){
            if (button == clickedButton){
                button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),
                    R.color.hintColor2))
                category = resources.getResourceEntryName(button.id)
            }else{
                button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),
                    R.color.background))
            }
        }
    }
}