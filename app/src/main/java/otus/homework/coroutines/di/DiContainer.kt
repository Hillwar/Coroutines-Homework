package otus.homework.coroutines.di

import otus.homework.coroutines.data.CatsService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiContainer {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val service by lazy { retrofit.create(CatsService::class.java) }

    companion object {
        const val imageHost = "https://api.thecatapi.com/v1/images/search"
    }
}