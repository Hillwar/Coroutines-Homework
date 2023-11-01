package otus.homework.coroutines.domain.repository

import otus.homework.coroutines.domain.models.CatFact

interface CatRepository {
    suspend fun getCatFact() : String
    suspend fun getCatImageUrl(): String
}