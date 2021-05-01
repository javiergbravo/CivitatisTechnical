package com.jgbravo.civitatistechnical.di

import com.jgbravo.civitatistechnical.data.datasource.JobDataSource
import com.jgbravo.civitatistechnical.data.datasource.JobDataSourceImpl
import com.jgbravo.civitatistechnical.data.manager.JobsManagerImpl
import com.jgbravo.civitatistechnical.data.remote.JobsApi
import com.jgbravo.civitatistechnical.data.remote.adapter.DateAdapter
import com.jgbravo.civitatistechnical.data.remote.utils.Urls
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJobsApi(): JobsApi {
        val moshi = Moshi.Builder()
            .add(DateAdapter())
            .build()

        return Retrofit.Builder()
            .baseUrl(Urls.JOBS_API_URL_BASE)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(OkHttpClient())
            .build()
            .create(JobsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideJobDataSource(api: JobsApi): JobDataSource = JobDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideJobManager(datasource: JobDataSource) = JobsManagerImpl(datasource)
}