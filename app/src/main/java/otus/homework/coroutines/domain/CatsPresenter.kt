package otus.homework.coroutines.domain

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import otus.homework.coroutines.domain.models.CatFact
import otus.homework.coroutines.domain.repository.CatRepository
import otus.homework.coroutines.domain.views.ICatsView
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException

class CatsPresenter(
    private val repository: CatRepository,
    private val catView: WeakReference<ICatsView>
) {

    private val coroutineScope = PresenterScope()
    private var job: Job? = null

    fun onInitComplete() {
        catView.get()?.apply {
            job = coroutineScope.launch {
                try {
                    coroutineScope {
                        val fact = async { repository.getCatFact() }
                        val imageUrl = async { repository.getCatImageUrl() }
                        val catFact = CatFact(fact.await(), imageUrl.await())
                        populate(catFact)
                    }
                } catch (e: SocketTimeoutException) {
                    showTimeoutExceptionToast()
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Throwable) {
                    CrashMonitor.trackWarning(e, this@CatsPresenter.javaClass.simpleName)
                    showToast(e.message ?: "Error")
                }
            }
        }
    }

    fun onStop() {
        job?.cancel()
    }

    fun detachView() {
        catView.clear()
        coroutineScope.cancel()
    }
}