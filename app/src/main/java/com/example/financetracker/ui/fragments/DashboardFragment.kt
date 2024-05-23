package com.example.financetracker.ui.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.financetracker.databinding.FragmentDashboardBinding
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding

    private lateinit var transactionViewModel: TransactionViewModel

    private lateinit var cashViewModel: CashViewModel
    private lateinit var cardViewModel: CardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)

        val sharedPreferences = context?.getSharedPreferences("logged_user_data", Context.MODE_PRIVATE)
        val email = sharedPreferences?.getString("email", "")

        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)

        transactionViewModel.getTransactionsByEmail(email)
        cashViewModel.getCashByEmail(email)
        cardViewModel.getCardsByEmail(email)

        val pieChart: PieChart = binding.entirebalancediagram

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(cardViewModel.getTotalBalance().toFloat(), "Карты"))
        entries.add(PieEntry(cashViewModel.getTotalBalance().toFloat(), "наличные"))

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(Color.HSVToColor(floatArrayOf(160.54f, 15.35f, 94.51f)),
            Color.HSVToColor(floatArrayOf(208.57f, 16.54f, 99.61f)))
        dataSet.valueTextSize = 10f
        dataSet.valueTextColor = Color.WHITE

        val data = PieData(dataSet)
        pieChart.data = data

        pieChart.description.isEnabled = false

        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(10f)
        pieChart.setDrawEntryLabels(false)
        pieChart.setUsePercentValues(false)
        pieChart.setDrawHoleEnabled(false)

        pieChart.invalidate()


        val pieChart2: PieChart = binding.structurediagramm

        val entries2 = ArrayList<PieEntry>()
        entries2.add(PieEntry(33f, "Еда"))
        entries2.add(PieEntry(39f, "Аренда"))
        entries2.add(PieEntry(2f, "Кредит"))
        entries2.add(PieEntry(39f, "Обучение"))
        entries2.add(PieEntry(40f, "Одежда"))
        entries2.add(PieEntry(39f, "Здоровье"))

        val dataSet2 = PieDataSet(entries2, "Структура расходов")
        dataSet2.colors = listOf(
            Color.rgb(255, 102, 102), // Еда
            Color.rgb(102, 153, 255), // Аренда
            Color.rgb(153, 102, 255), // Кредит
            Color.rgb(255, 153, 255), // Обучение
            Color.rgb(255, 204, 102), // Одежда
            Color.rgb(153, 255, 204)  // Здоровье
        )
        dataSet2.valueTextSize = 12f
        dataSet2.valueTextColor = Color.BLACK
        dataSet2.setValueLinePart1OffsetPercentage(80f)
        dataSet2.setValueLinePart1Length(0.3f)
        dataSet2.setValueLinePart2Length(0.4f)
        dataSet2.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet2.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE

        val data2 = PieData(dataSet2)
        pieChart2.data = data2

        // Убираем описание диаграммы
        pieChart2.description.isEnabled = false

        // Настройка легенды
        val legend2 = pieChart2.legend
        legend2.isEnabled = true
        legend2.textSize = 12f
        legend2.form = com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE

        pieChart2.setEntryLabelColor(Color.BLACK)
        pieChart2.setEntryLabelTextSize(12f)
        pieChart2.setUsePercentValues(false)
        pieChart2.setDrawHoleEnabled(false)

        pieChart2.invalidate()


        val barChart: BarChart = binding.balancediagramm

        val entriesBalance = listOf(
            BarEntry(0f, 40f, "Биткоин"),
            BarEntry(1f, 88f, "Сбер"),
            BarEntry(2f, 60f, "Наличные"),
            BarEntry(3f, 35f, "Догикоин"),
            BarEntry(4f, 70f, "ВТБ")
        )

        val dataSetBalance = BarDataSet(entriesBalance, "Баланс")
        dataSetBalance.colors = listOf(
            Color.rgb(153, 255, 204), // Биткоин
            Color.rgb(102, 153, 255), // Сбер
            Color.rgb(255, 204, 204), // Наличные
            Color.rgb(153, 255, 204), // Догикоин
            Color.rgb(102, 153, 255)  // ВТБ
        )
        dataSetBalance.valueTextSize = 12f
        dataSetBalance.valueTextColor = Color.BLACK

        val dataBalance = BarData(dataSetBalance)
        barChart.data = dataBalance

        // Настройка осей
        barChart.axisRight.isEnabled = false
        val leftAxis = barChart.axisLeft
        leftAxis.axisMinimum = 0f

        val xAxis = barChart.xAxis
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawLabels(true)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return entriesBalance[value.toInt()].data.toString()
            }
        }
        // Убираем описание диаграммы
        barChart.description.isEnabled = false

        // Настройка легенды
        val legend = barChart.legend
        legend.isEnabled = false

        barChart.invalidate()


        return binding.root
    }
}