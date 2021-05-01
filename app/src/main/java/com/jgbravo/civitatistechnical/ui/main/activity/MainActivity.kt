package com.jgbravo.civitatistechnical.ui.main.activity

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.jgbravo.civitatistechnical.R
import com.jgbravo.civitatistechnical.ui.main.activity.adapter.JobResumeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var container: ConstraintLayout
    private lateinit var loader: ProgressBar
    private lateinit var jobRecyclerView: RecyclerView

    private lateinit var rvAdapter: JobResumeAdapter

    protected fun getLayoutID(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onPostCreate()
        collectFlows()
    }

    protected fun onPostCreate() {
        setContentView(getLayoutID())
        bindViews()
        configureViews()
    }

    protected fun bindViews() {
        container = findViewById(R.id.container)
        loader = findViewById(R.id.loader)
        jobRecyclerView = findViewById(R.id.jobRecycler)
    }


    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.getJobs()
        }
    }

    private fun configureViews() {
        rvAdapter = JobResumeAdapter()
        jobRecyclerView.apply {
            adapter = rvAdapter
        }
        rvAdapter.setOnItemClickListener { job ->
            val result = "Job pressed" // TODO: borrar
        }
    }

    private fun collectFlows() {
        lifecycleScope.launchWhenCreated {
            viewModel.jobState.collect { state ->
                when (state) {
                    is MainViewModel.MainState.Loading -> {
                        showLoader()
                    }
                    is MainViewModel.MainState.Error -> {

                    }
                    is MainViewModel.MainState.Success -> {
                        rvAdapter.submitList(state.jobList)
                        hideLoader()
                    }
                    else -> Unit
                }
            }
        }
    }

    protected fun showLoader() {
        container.visibility = View.GONE
        loader.visibility = View.VISIBLE
    }

    protected fun hideLoader() {
        container.visibility = View.VISIBLE
        loader.visibility = View.GONE
    }
}