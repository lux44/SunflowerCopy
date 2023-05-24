package com.example.ksptest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ksptest.api.UnsplashService
import com.example.ksptest.data.UnsplashPagingSource
import com.example.ksptest.data.UnsplashPhoto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val service: UnsplashService){

    fun getSearchResultStream(query : String): Flow<PagingData<UnsplashPhoto>>{
        return Pager(
            config =  PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { UnsplashPagingSource(service, query) }
        ).flow
    }
    companion object{
        const val NETWORK_PAGE_SIZE = 25
    }
}