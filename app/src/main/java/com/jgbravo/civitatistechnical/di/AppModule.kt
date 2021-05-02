package com.jgbravo.civitatistechnical.di

import com.google.gson.GsonBuilder
import com.jgbravo.civitatistechnical.data.datasource.JobDataSource
import com.jgbravo.civitatistechnical.data.datasource.JobDataSourceImpl
import com.jgbravo.civitatistechnical.data.manager.JobsManager
import com.jgbravo.civitatistechnical.data.manager.JobsManagerImpl
import com.jgbravo.civitatistechnical.data.remote.JobsApi
import com.jgbravo.civitatistechnical.data.remote.utils.Urls
import com.jgbravo.civitatistechnical.utils.DateConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideJobsApi(): JobsApi {
        val gson = GsonBuilder()
            .setDateFormat(DateConstants.DEFAULT_PATTERN)
            .create()

        return Retrofit.Builder()
            .baseUrl(Urls.JOBS_API_URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient())
            .build()
            .create(JobsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideJobDataSource(api: JobsApi): JobDataSource = JobDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideJobManager(datasource: JobDataSource): JobsManager = JobsManagerImpl(datasource)
}