package com.example.financetracker.ui.fragments

import android.content.res.ColorStateList
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.financetracker.R
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.FragmentAddTransactionBinding
import com.example.financetracker.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTransactionFragment : Fragment() {
    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var viewModel: TransactionViewModel
    var flag: Boolean = true
    var isIncome: Boolean = true
    private var category: String = ""
    private var buttonsList: MutableList<ImageButton> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTransactionBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)

        binding.textExpense.setOnClickListener() {
            binding.textViewPlus.text = "-"
            isIncome = false
            switchTextDesign(binding.textIncome, binding.textExpense)
        }
        binding.textIncome.setOnClickListener() {
            binding.textViewPlus.text = "+"
            isIncome = true
            switchTextDesign(binding.textExpense, binding.textIncome)
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

        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val dateString = dateFormat.format(currentDate)
        binding.btnAcs.setOnClickListener(){
            var transaction = Transaction(
                isIncome = isIncome,
                isCreditCard = flag,
                transactionName = binding.editTransaction.text.toString(),
                category = category,
                amount = binding.editTextNumber2.text.toString().toInt(),
                date = dateString
            )
            viewModel.addTransaction(transaction)
            Toast.makeText(context, "Ваша транзакция успешно добавлена!", Toast.LENGTH_SHORT).show()
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

    private fun switchTextDesign(textFieldToUpdate: TextView, textFieldToCopy: TextView) {
        val textColor = textFieldToUpdate.currentTextColor
        val isUnderlined = textFieldToUpdate.paintFlags and Paint.UNDERLINE_TEXT_FLAG != 0

        textFieldToUpdate.setTextColor(textFieldToCopy.currentTextColor)
        textFieldToUpdate.paintFlags = if (textFieldToCopy.paintFlags and Paint.UNDERLINE_TEXT_FLAG != 0) Paint.UNDERLINE_TEXT_FLAG else 0

        textFieldToCopy.setTextColor(textColor)
        textFieldToCopy.paintFlags = if (isUnderlined) Paint.UNDERLINE_TEXT_FLAG else 0
    }
}