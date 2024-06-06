package com.example.financetracker.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financetracker.data.Advice
import com.example.financetracker.databinding.FragmentAdvicesBinding
import com.example.financetracker.ui.adapters.AdviceAdapter
import com.example.financetracker.ui.adapters.CardAdapter

class AdvicesFragment : Fragment() {

    private lateinit var binding: FragmentAdvicesBinding
    private lateinit var adviceAdapter: AdviceAdapter
    private val advicesList = listOf(
        Advice("Как начать инвестировать с нуля", "Эта статья предлагает подробный обзор основ инвестирования для начинающих.\n" +
                "такие как акции, облигации, фонды и диверсификация портфеля.\n" +
                "Также рассматриваются стратегии инвестирования\n" +
                "и советы по выбору правильных инвестиций\n" +
                "для ваших целей."),
        Advice("Как начать инвестировать с нуля", "Эта статья предлагает подробный обзор основ инвестирования для начинающих.\n" +
                "такие как акции, облигации, фонды и диверсификация портфеля.\n" +
                "Также рассматриваются стратегии инвестирования\n" +
                "и советы по выбору правильных инвестиций\n" +
                "для ваших целей."),
        Advice("Как начать инвестировать с нуля", "Эта статья предлагает подробный обзор основ инвестирования для начинающих.\n" +
                "такие как акции, облигации, фонды и диверсификация портфеля.\n" +
                "Также рассматриваются стратегии инвестирования\n" +
                "и советы по выбору правильных инвестиций\n" +
                "для ваших целей.")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdvicesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adviceAdapter = AdviceAdapter(advicesList, requireContext())
        binding.recycleAdvices.layoutManager = LinearLayoutManager(context)
        binding.recycleAdvices.adapter = adviceAdapter
    }
}