package otus.homework.coroutines.domain

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class PresenterScope : CoroutineScope {
    private val job = SupervisorJob()
    private val name = CoroutineName("CatsCoroutine")

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + name + job
}