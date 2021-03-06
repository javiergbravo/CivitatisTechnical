package com.jgbravo.civitatistechnical.ui.jobdetail

import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.jgbravo.civitatistechnical.R
import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.ui.NavigationConstants
import com.jgbravo.civitatistechnical.ui.base.BaseActivity
import com.jgbravo.civitatistechnical.ui.company.CompanyWebViewActivity
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
    private lateinit var type: TextView
    private lateinit var link: TextView
    private lateinit var createdDay: TextView
    private lateinit var jobDescription: TextView

    override fun getLayoutID(): Int = R.layout.activity_job_detail
    override fun toolbarTitle(): Int = R.string.job_details_title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadBundles()
        setUpToolbar()
        collectFlows()
    }

    override fun setUpToolbar() {
        super.setUpToolbar()
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
        type = findViewById(R.id.type)
        link = findViewById(R.id.link)
        createdDay = findViewById(R.id.created_day)
        jobDescription = findViewById(R.id.job_description)
    }

    override fun configureViews() {
        currentJob?.let { job ->
            jobTitle.text = job.title
            companyName.text = job.company
            location.text = job.location
            link.text = job.companyUrl ?: ""
            if (job.type == null) {
                type.visibility = View.GONE
            } else {
                type.text = job.type
            }
            if (job.createdAt == null) {
                createdDay.visibility = View.GONE
            } else {
                createdDay.text = convertDateToShortString(job.createdAt)
            }
            jobDescription.text = job.description.convertFromHTML()

            Glide.with(this)
                .load(job.companyLogo)
                .error(R.drawable.ic_image_error)
                .fitCenter()
                .into(companyLogo)

            if (link.text.isNullOrEmpty()) {
                link.visibility = View.GONE
            } else {
                link.setOnClickListener {
                    val intent = Intent(this, CompanyWebViewActivity::class.java)
                    intent.putExtra(NavigationConstants.LINK_BUNDLE, currentJob?.companyUrl)
                    startActivity(intent)
                }
            }
            jobDescription.movementMethod = LinkMovementMethod.getInstance()
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
                        showError()
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