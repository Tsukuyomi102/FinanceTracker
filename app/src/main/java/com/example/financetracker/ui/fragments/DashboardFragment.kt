package com.example.financetracker.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.financetracker.databinding.FragmentDashboardBinding
import com.example.financetracker.viewmodel.CardViewModel
import com.example.financetracker.viewmodel.CashViewModel
import com.example.financetracker.viewmodel.TransactionViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        loadData()
    }

    private fun initViewModels() {
        transactionViewModel = ViewModelProvider(requireActivity()).get(TransactionViewModel::class.java)
        cashViewModel = ViewModelProvider(requireActivity()).get(CashViewModel::class.java)
        cardViewModel = ViewModelProvider(requireActivity()).get(CardViewModel::class.java)
    }

    private fun loadData() {
        cardViewModel.getCardsLiveData().observe(viewLifecycleOwner) { cards ->
            cashViewModel.getCashesLiveData().observe(viewLifecycleOwner) { cashes ->
                changeBalance()
                setupPieChart1()
                setupBarChart()
            }
        }

        transactionViewModel.getTransactionLiveData().observe(viewLifecycleOwner) { transactions ->
            setupPieChart2()
        }
    }

    private fun changeBalance() {
        binding.textViewBalance.text = (cashViewModel.getTotalBalance() + cardViewModel.getTotalBalance()).toString()
        binding.textViewForNal.text = cashViewModel.getTotalBalance().toString()
        binding.textViewForCard.text = cardViewModel.getTotalBalance().toString()
    }

    private fun setupPieChart1() {
        val pieChart: PieChart = binding.entirebalancediagram

        val entries = listOf(
            PieEntry(cardViewModel.getTotalBalance().toFloat()),
            PieEntry(cashViewModel.getTotalBalance().toFloat())
        )

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = listOf(
            Color.rgb(212, 234, 254),
            Color.rgb(204, 241, 229)
        )
        dataSet.valueTextSize = 10f
        dataSet.valueTextColor = Color.BLACK

        val data = PieData(dataSet)
        pieChart.data = data

        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(10f)
        pieChart.setDrawEntryLabels(false)
        pieChart.setUsePercentValues(false)
        pieChart.setDrawHoleEnabled(false)

        pieChart.invalidate()
    }

    private fun setupPieChart2() {
        val pieChart2: PieChart = binding.structurediagramm

        val entries2 = transactionViewModel.getTransactionEntriesForPieChart()
        val dataSet2 = PieDataSet(entries2, "")
        dataSet2.colors = listOf(
            Color.rgb(204, 241, 229),
            Color.rgb(255, 208, 208),
            Color.rgb(204, 210, 241),
            Color.rgb(255, 226, 191),
            Color.rgb(212, 234, 254),
            Color.rgb(244, 214, 255),
            Color.rgb(252,181,252),
            Color.rgb(128,204,179),
            Color.rgb(255,147,147),
            Color.rgb(126,255,213),
            Color.rgb(219,219,221)
        )
        dataSet2.valueTextSize = 10f
        dataSet2.valueTextColor = Color.BLACK
        dataSet2.setValueLinePart1OffsetPercentage(80f)
        dataSet2.setValueLinePart1Length(0.3f)
        dataSet2.setValueLinePart2Length(0.4f)
        dataSet2.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet2.xValuePosition = PieDataSet.ValuePosition.INSIDE_SLICE
        dataSet2.setDrawIcons(false)

        val data2 = PieData(dataSet2)
        pieChart2.data = data2

        pieChart2.description.isEnabled = false

        val legend2 = pieChart2.legend
        legend2.isEnabled = true
        legend2.textSize = 10f
        legend2.form = com.github.mikephil.charting.components.Legend.LegendForm.CIRCLE
        legend2.verticalAlignment = com.github.mikephil.charting.components.Legend.LegendVerticalAlignment.TOP
        legend2.horizontalAlignment = com.github.mikephil.charting.components.Legend.LegendHorizontalAlignment.RIGHT
        legend2.orientation = com.github.mikephil.charting.components.Legend.LegendOrientation.VERTICAL
        legend2.setDrawInside(false)
        legend2.xOffset = 0f
        legend2.xEntrySpace = 20f

        pieChart2.setEntryLabelColor(Color.BLACK)
        pieChart2.setEntryLabelTextSize(12f)
        pieChart2.setUsePercentValues(true)
        pieChart2.setDrawEntryLabels(false)
        pieChart2.setDrawHoleEnabled(false)

        pieChart2.invalidate()
    }

    private fun setupBarChart() {
        val barChart: BarChart = binding.balancediagramm

        val entries = mutableListOf<BarEntry>()

        cardViewModel.cardsList.forEachIndexed { index, card ->
            entries.add(BarEntry(index.toFloat(), card.cardBalance.toFloat(), card.cardName))
        }
        cashViewModel.cashList.forEachIndexed { index, cash ->
            entries.add(BarEntry((cardViewModel.cardsList.size + index).toFloat(), cash.cashBalance.toFloat(), cash.cashName))
        }

        val dataSet = BarDataSet(entries, "Баланс")
        val colors = mutableListOf<Int>()
        colors.addAll(cardViewModel.cardsList.map { Color.rgb((0..255).random(), (0..255).random(), (0..255).random()) })
        colors.addAll(cashViewModel.cashList.map { Color.rgb((0..255).random(), (0..255).random(), (0..255).random()) })
        dataSet.colors = colors
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.BLACK

        val data = BarData(dataSet)
        barChart.data = data

        barChart.axisRight.isEnabled = false
        val leftAxis = barChart.axisLeft
        leftAxis.axisMinimum = 0f

        val xAxis = barChart.xAxis
        xAxis.position = com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawLabels(true)
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val entryIndex = value.toInt()
                return if (entryIndex < entries.size) {
                    entries[entryIndex].data.toString()
                } else {
                    ""
                }
            }
        }
        barChart.description.isEnabled = false

        val legend = barChart.legend
        legend.isEnabled = false

        barChart.invalidate()
    }
}
