package com.jgbravo.civitatistechnical.ui.main.adapter

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
import com.jgbravo.civitatistechnical.data.dtos.entity.Job
import com.jgbravo.civitatistechnical.utils.convertDateToShortString

class JobResumeAdapter : RecyclerView.Adapter<JobResumeAdapter.JobViewHolder>() {

    private lateinit var context: Context

    private val diffCallback = object : DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    private var onItemClickListener: ((Job) -> Unit)? = null

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

    fun submitList(list: List<Job>) {
        differ.submitList(list)
    }

    fun setOnItemClickListener(listener: (Job) -> Unit) {
        onItemClickListener = listener
    }

    inner class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val jobTitle = itemView.findViewById<TextView>(R.id.job_title)
        private val companyName = itemView.findViewById<TextView>(R.id.company_name)
        private val companyLogo = itemView.findViewById<ImageView>(R.id.company_logo)
        private val location = itemView.findViewById<TextView>(R.id.location)
        private val createdDay = itemView.findViewById<TextView>(R.id.created_day)

        fun bind(job: Job, context: Context) {
            jobTitle.text = job.title
            companyName.text = job.company
            location.text = job.location
            createdDay.text = convertDateToShortString(job.createdAt)


            itemView.setOnClickListener {
                onItemClickListener?.let {
                    it(job)
                }
            }

            Glide.with(itemView)
                .load(job.companyLogo)
                .error(R.drawable.ic_image_error)
                .fitCenter()
                .into(companyLogo)
        }
    }
}