package com.example.financetracker.ui.fragments

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Paint
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.R
import com.example.financetracker.api.ApiService
import com.example.financetracker.data.Card
import com.example.financetracker.data.Cash
import com.example.financetracker.data.Transaction
import com.example.financetracker.databinding.FragmentAddTransactionBinding
import com.example.financetracker.network.RetrofitClient
import com.example.financetracker.ui.adapters.CardAdapter
import com.example.financetracker.ui.adapters.CashAdapter
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTransactionFragment : Fragment(), CardAdapter.OnCardClickListener, CashAdapter.OnCashClickListener {
    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var viewModel: TransactionViewModel

    private lateinit var cardViewModel: CardViewModel
    private lateinit var cardAdapter: CardAdapter
    private var selectedCardNumber: Long = 0

    private lateinit var cashViewModel: CashViewModel
    private lateinit var cashAdapter: CashAdapter
    private var selectedCashName: String = ""

    private var flag: Boolean = true
    private var isIncome: Boolean = true
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

        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
        cardAdapter = CardAdapter(cardViewModel.cardsList, requireContext(), this)
        binding.List.layoutManager = LinearLayoutManager(context)
        binding.List.adapter = cardAdapter

        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        cashAdapter = CashAdapter(cashViewModel.cashList, requireContext(), this)


        binding.textExpense.setOnClickListener() {
            binding.textViewPlus.text = "-"
            isIncome = false
            switchTextDesign(binding.textIncome, binding.textExpense)
            switchImagesExpense()
        }
        binding.textIncome.setOnClickListener() {
            binding.textViewPlus.text = "+"
            isIncome = true
            switchTextDesign(binding.textExpense, binding.textIncome)
            switchImagesIncome()
        }

        chooseCategory()

        binding.btnCar.setOnClickListener() {
            changeColor(binding.btnCar, binding.btnNal)
            flag = true
            binding.textList.text = "Банковские счета"
            cardTransaction(cardAdapter)
        }
        binding.btnNal.setOnClickListener() {
            changeColor(binding.btnNal, binding.btnCar)
            binding.textList.text = "Наличные"
            flag = false
            cashTransaction(cashAdapter)
        }

        binding.btnAcs.setOnClickListener(){
            addTransaction()
        }


        return binding.root
    }

    private fun changeColor(firstButton: Button, secondButton: Button) {
        val buttonColorLight = R.color.buttonColor
        val buttonTextColorLight = R.color.white
        val buttonColorDark = R.color.backgroundDark
        val buttonTextColorDark = R.color.white

        val buttonColor = if (isDarkTheme()) buttonColorDark else buttonColorLight
        val buttonTextColor = if (isDarkTheme()) buttonTextColorDark else buttonTextColorLight

        firstButton.setBackgroundColor(ContextCompat.getColor(requireContext(), buttonColor))
        firstButton.setTextColor(ContextCompat.getColor(requireContext(), buttonTextColor))

        val secondButtonColor = if (isDarkTheme()) buttonColorLight else buttonColorDark
        val secondButtonTextColor = if (isDarkTheme()) buttonTextColorLight else buttonTextColorDark

        secondButton.setBackgroundColor(ContextCompat.getColor(requireContext(), secondButtonColor))
        secondButton.setTextColor(ContextCompat.getColor(requireContext(), secondButtonTextColor))
    }

    private fun changeButtonBackground(clickedButton: ImageButton, buttonsList: List<ImageButton>) {
        val hintColor2Light = R.color.hintColor2
        val backgroundLight = R.color.background
        val backgroundDark = R.color.backgroundDark

        val background = if (isDarkTheme()) backgroundDark else backgroundLight

        for (button in buttonsList){
            if (button == clickedButton){
                button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), hintColor2Light))
                category = resources.getResourceEntryName(button.id)
            } else {
                button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), background))
            }
        }
    }

    private fun isDarkTheme(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    private fun switchTextDesign(textFieldToUpdate: TextView, textFieldToCopy: TextView) {
        val textColor = textFieldToUpdate.currentTextColor
        val isUnderlined = textFieldToUpdate.paintFlags and Paint.UNDERLINE_TEXT_FLAG != 0

        textFieldToUpdate.setTextColor(textFieldToCopy.currentTextColor)
        textFieldToUpdate.paintFlags = if (textFieldToCopy.paintFlags and Paint.UNDERLINE_TEXT_FLAG != 0) Paint.UNDERLINE_TEXT_FLAG else 0

        textFieldToCopy.setTextColor(textColor)
        textFieldToCopy.paintFlags = if (isUnderlined) Paint.UNDERLINE_TEXT_FLAG else 0
    }

    private fun switchImagesExpense(){
        var imageButtonMoney = binding.imageMoney
        imageButtonMoney.setImageResource(R.drawable.icon_food_circle)
        imageButtonMoney.id = R.id.imageFood

        var imageButtonBusiness = binding.imageBusiness
        imageButtonBusiness.setImageResource(R.drawable.icon_health_circle)
        imageButtonBusiness.id = R.id.imageHealth

        var imageButtonInvestment = binding.imageInvestment
        imageButtonInvestment.setImageResource(R.drawable.icon_beauty_cicle)
        imageButtonInvestment.id = R.id.imageBeauty
    }

    private fun switchImagesIncome(){
        var imageButtonFood = binding.imageMoney
        imageButtonFood.setImageResource(R.drawable.icon_money_circle)
        imageButtonFood.id = R.id.imageMoney

        var imageButtonHealth = binding.imageBusiness
        imageButtonHealth.setImageResource(R.drawable.icon_business_circle)
        imageButtonHealth.id = R.id.imageHealth

        var imageButtonBeauty = binding.imageInvestment
        imageButtonBeauty.setImageResource(R.drawable.icon_investment_circle)
        imageButtonHealth.id = R.id.imageBeauty
    }

    private fun cardTransaction(cardAdapter: CardAdapter){
        binding.List.layoutManager = LinearLayoutManager(context)
        binding.List.adapter = cardAdapter
    }

    private fun cashTransaction(cashAdapter: CashAdapter){
        binding.List.layoutManager = LinearLayoutManager(context)
        binding.List.adapter = cashAdapter
    }

    private fun chooseCategory(){
        for (i in 0 until binding.tableLayout.childCount){
            val tableRow: TableRow = binding.tableLayout.getChildAt(i) as TableRow
            for(j in 0 until tableRow.childCount) {
                val ImageButton: ImageButton = tableRow.getChildAt(j) as ImageButton
                buttonsList.add(ImageButton)
                ImageButton.setOnClickListener(){
                    changeButtonBackground(ImageButton, buttonsList)
                }
            }
        }
    }

    private fun addTransaction(){
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val dateString = dateFormat.format(currentDate)

        var transaction = Transaction(
            isIncome = isIncome,
            isCreditCard = flag,
            transactionName = binding.editTransaction.text.toString(),
            category = category,
            amount = binding.editTextNumber2.text.toString().toInt(),
            date = dateString,
            cardNumber = selectedCardNumber,
            cashName = selectedCashName
        )
        val apiService = RetrofitClient.retrofit.create(ApiService::class.java)
        val call = apiService.addTransaction(
            email = "test@mail.ru",
            isIncome = transaction.isIncome,
            isCreditCard = transaction.isCreditCard,
            name = transaction.transactionName,
            category = transaction.category,
            amount = transaction.amount,
            date = transaction.date,
            cardNumber = transaction.cardNumber,
            cashName = transaction.cashName
        )

        call.enqueue(object : Callback<Integer> {
            override fun onResponse(call: Call<Integer>, response: Response<Integer>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Транзакция успешно добавлена", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Ошибка при добавлении транзакции", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Integer>, t: Throwable) {
                Toast.makeText(context, "Ошибка при выполнении запроса", Toast.LENGTH_SHORT).show()
            }
        })
        if (transaction.isCreditCard){
            viewModel.addCardTransaction(transaction, cardViewModel = cardViewModel, selectedCardNumber = selectedCardNumber)
        } else {
            viewModel.addCashTransaction(transaction, cashViewModel = cashViewModel, selectedCashName = selectedCashName)
        }
        Toast.makeText(context, "Ваша транзакция успешно добавлена", Toast.LENGTH_SHORT).show()

    }

    override fun onCardClick(card: Card) {
        selectedCardNumber = card.cardNumber
        Toast.makeText(context, "Вы выбрали карту с названием ${card.cardName}", Toast.LENGTH_SHORT).show()
    }

    override fun onCashClick(cash: Cash) {
        selectedCashName = cash.cashName
        Toast.makeText(context, "Вы выбрали наличные с названием ${cash.cashName}", Toast.LENGTH_SHORT).show()
    }
}