package com.jgbravo.civitatistechnical.ui.jobdetail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jgbravo.civitatistechnical.R
import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.ui.NavigationConstants
import com.jgbravo.civitatistechnical.ui.base.BaseActivity
import com.jgbravo.civitatistechnical.utils.convertDateToShortString
import com.jgbravo.civitatistechnical.utils.convertFromHTML
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class JobDetailActivity : BaseActivity() {

    private val viewModel: JobDetailViewModel by viewModels()

    private var currentJob: Job? = null

    private lateinit var jobTitle: TextView
    private lateinit var companyName: TextView
    private lateinit var companyLogo: ImageView
    private lateinit var location: TextView
    private lateinit var link: TextView
    private lateinit var createdDay: TextView
    private lateinit var jobDescription: TextView

    override fun getLayoutID(): Int = R.layout.activity_job_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBundles()
        setUpToolbar()
        collectFlows()
    }

    private fun setUpToolbar() {
        supportActionBar?.title = resources.getString(R.string.job_details)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadBundles() {
        if (intent.hasExtra(NavigationConstants.JOB_BUNDLE)) {
            intent.extras?.getString(NavigationConstants.JOB_BUNDLE)?.let {
                lifecycleScope.launchWhenCreated {
                    viewModel.getJobByID(it)
                }
            }
        }
    }

    override fun bindViews() {
        super.bindViews()
        jobTitle = findViewById(R.id.job_title)
        companyName = findViewById(R.id.company_name)
        companyLogo = findViewById(R.id.company_logo)
        location = findViewById(R.id.location)
        link = findViewById(R.id.link)
        createdDay = findViewById(R.id.created_day)
        jobDescription = findViewById(R.id.job_description)
    }

    override fun configureViews() {
        currentJob?.let {
            jobTitle.text = it.title
            companyName.text = it.company
            location.text = it.location
            link.text = it.companyUrl ?: ""
            createdDay.text = convertDateToShortString(it.createdAt)
            jobDescription.text = it.description.convertFromHTML()

            Glide.with(this)
                .load(it.companyLogo)
                .error(R.drawable.ic_image_error)
                .fitCenter()
                .into(companyLogo)
        }
    }

    private fun collectFlows() {
        lifecycleScope.launchWhenStarted {
            viewModel.jobDetailState.collect { state ->
                when (state) {
                    is JobDetailViewModel.JobDetailState.Loading -> {
                        showLoader()
                    }
                    is JobDetailViewModel.JobDetailState.Error -> {
                        hideLoader()
                    }
                    is JobDetailViewModel.JobDetailState.Success -> {
                        currentJob = state.job
                        configureViews()
                        hideLoader()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}