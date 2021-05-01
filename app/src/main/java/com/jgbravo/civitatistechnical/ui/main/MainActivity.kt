package com.jgbravo.civitatistechnical.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.jgbravo.civitatistechnical.R
import com.jgbravo.civitatistechnical.ui.NavigationConstants
import com.jgbravo.civitatistechnical.ui.base.BaseActivity
import com.jgbravo.civitatistechnical.ui.jobdetail.JobDetailActivity
import com.jgbravo.civitatistechnical.ui.main.adapter.JobResumeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var jobRecyclerView: RecyclerView

    private lateinit var rvAdapter: JobResumeAdapter

    override fun getLayoutID(): Int = R.layout.activity_main
    override fun toolbarTitle(): Int = R.string.job_list_title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        collectFlows()
    }

    override fun bindViews() {
        super.bindViews()
        jobRecyclerView = findViewById(R.id.jobRecycler)
    }


    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.getJobs()
        }
    }

    override fun configureViews() {
        rvAdapter = JobResumeAdapter()
        jobRecyclerView.apply {
            adapter = rvAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
        }
        rvAdapter.setOnItemClickListener { job ->
            val intent = Intent(this, JobDetailActivity::class.java)
            intent.putExtra(NavigationConstants.JOB_BUNDLE, job.id)
            startActivity(intent)
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
                        hideLoader()
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
}