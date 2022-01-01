package com.abdullah996.artbooktesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullah996.artbooktesting.model.ImageResponse
import com.abdullah996.artbooktesting.roomdb.Art
import com.abdullah996.artbooktesting.util.Resource

class FakeArtRepository:ArtRepositoryInterface {

    private val arts= mutableListOf<Art>()
    private val artLiveData=MutableLiveData<List<Art>>(arts)
    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artLiveData.postValue(arts)
    }
}