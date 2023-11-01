package otus.homework.coroutines.domain.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import otus.homework.coroutines.domain.CrashMonitor
import otus.homework.coroutines.domain.models.CatFact
import otus.homework.coroutines.domain.repository.CatRepository
import java.net.SocketTimeoutException

class MainViewModel(
    private val repository: CatRepository,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        CrashMonitor.trackWarning(throwable, javaClass.simpleName)
        _result.value = Result.Error(throwable)
    }
    private var job: Job? = null

    private val _result = MutableStateFlow<Result>(Result.Idle)
    val result = _result.asStateFlow()

    fun onInitComplete() {
        job = viewModelScope.launch(exceptionHandler) {
            val fact = async { repository.getCatFact() }
            val imageUrl = async { repository.getCatImageUrl() }
            val catFact = CatFact(fact.await(), imageUrl.await())
            _result.value = (Result.Success(catFact))
        }
    }

    fun onStop() {
        job?.cancel()
    }
}

sealed class Result {
    object Idle : Result()
    data class Success(val catFact: CatFact) : Result()
    data class Error(val throwable: Throwable) : Result()
}