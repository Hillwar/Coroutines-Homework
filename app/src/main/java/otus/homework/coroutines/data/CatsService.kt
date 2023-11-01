package otus.homework.coroutines.data

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface CatsService {

    @GET("fact")
    suspend fun getCatFact() : Fact

    @GET
    suspend fun getCatImage(@Url url: String): List<Image>
}