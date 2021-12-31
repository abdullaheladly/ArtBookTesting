package com.abdullah996.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.abdullah996.artbooktesting.model.ImageResponse
import com.abdullah996.artbooktesting.roomdb.Art
import com.abdullah996.artbooktesting.util.Resource


interface ArtRepositoryInterface {
    suspend fun insertArt(art : Art)

    suspend fun deleteArt(art: Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}