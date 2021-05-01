package com.jgbravo.civitatistechnical.ui.main.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jgbravo.civitatistechnical.R
import com.jgbravo.civitatistechnical.data.dtos.entity.JobDetails
import com.jgbravo.civitatistechnical.utils.convertDateToShortString

class JobResumeAdapter : RecyclerView.Adapter<JobResumeAdapter.JobViewHolder>() {

    private lateinit var context: Context

    private val diffCallback = object : DiffUtil.ItemCallback<JobDetails>() {
        override fun areItemsTheSame(oldItem: JobDetails, newItem: JobDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobDetails, newItem: JobDetails): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    private var onItemClickListener: ((JobDetails) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.job_resume_item, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = differ.currentList[position]

        holder.bind(job, context)
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun submitList(list: List<JobDetails>) {
        differ.submitList(list)
    }

    fun setOnItemClickListener(listener: (JobDetails) -> Unit) {
        onItemClickListener = listener
    }

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobTitle = itemView.findViewById<TextView>(R.id.job_title)
        val companyName = itemView.findViewById<TextView>(R.id.company_name)
        val companyLogo = itemView.findViewById<ImageView>(R.id.company_logo)
        val location = itemView.findViewById<TextView>(R.id.location)
        val created_day = itemView.findViewById<TextView>(R.id.created_day)

        fun bind(job: JobDetails, context: Context) {
            jobTitle.text = job.title
            companyName.text = job.company
            location.text = job.location
            created_day.text = convertDateToShortString(job.createdAt)

            Glide.with(itemView)
                .load(job.companyLogo)
                .error(R.drawable.ic_image_error)
                .fitCenter()
                .into(companyLogo)
        }
    }
}