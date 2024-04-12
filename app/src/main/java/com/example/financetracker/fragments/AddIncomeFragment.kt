package com.example.financetracker.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableRow
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.financetracker.R
import com.example.financetracker.databinding.FragmentAddIncomeBinding

class AddIncomeFragment : Fragment() {
    private lateinit var binding: FragmentAddIncomeBinding
    private var flag: Boolean = false
    private var category: String = ""
    private var ButtonId: Int = 0
    private var buttonsList: MutableList<ImageButton> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddIncomeBinding.inflate(layoutInflater, container, false)
        binding.textExpense.setOnClickListener() {
            findNavController().navigate(R.id.action_addIncomeFragment_to_addExpenseFragment)
        }
        binding.btnNal.setOnClickListener() {
            changeColor(binding.btnNal, binding.btnCar)
            flag = true
        }

        binding.btnCar.setOnClickListener() {
            changeColor(binding.btnCar, binding.btnNal)
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
                Toast.makeText(context, category, Toast.LENGTH_LONG).show()
            }else{
                button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(),
                    R.color.background))
            }
        }
    }
}