package com.gameloop.laboratorioclinicoproc.views.testresults

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gameloop.laboratorioclinicoproc.R

class TestsResultsFragment : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tests_results, container, false)
    }
}