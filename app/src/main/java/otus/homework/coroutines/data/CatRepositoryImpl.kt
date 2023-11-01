package otus.homework.coroutines.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import otus.homework.coroutines.domain.CrashMonitor
import otus.homework.coroutines.domain.models.CatFact
import otus.homework.coroutines.domain.repository.CatRepository
import otus.homework.coroutines.di.DiContainer
import java.lang.Exception
import java.net.SocketTimeoutException

class CatRepositoryImpl(private val service: CatsService) : CatRepository {

    override suspend fun getCatFact(): String = withContext(Dispatchers.IO) {
        service.getCatFact().fact
    }

    override suspend fun getCatImageUrl(): String = withContext(Dispatchers.IO) {
        service.getCatImage(DiContainer.imageHost).first().url
    }
}