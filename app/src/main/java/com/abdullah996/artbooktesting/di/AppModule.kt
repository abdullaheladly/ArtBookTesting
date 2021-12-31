package com.abdullah996.artbooktesting.di


import android.content.Context
import androidx.room.Room
import com.abdullah996.artbooktesting.R
import com.abdullah996.artbooktesting.api.RetrofitAPI
import com.abdullah996.artbooktesting.repo.ArtRepository
import com.abdullah996.artbooktesting.repo.ArtRepositoryInterface
import com.abdullah996.artbooktesting.roomdb.ArtDao
import com.abdullah996.artbooktesting.roomdb.ArtDatabase
import com.abdullah996.artbooktesting.util.Util.BASE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun  injectRoomDatabase(@ApplicationContext context: Context)=Room.databaseBuilder(
        context,ArtDatabase::class.java,"ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database:ArtDatabase)=database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi():RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api: RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface
    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}
