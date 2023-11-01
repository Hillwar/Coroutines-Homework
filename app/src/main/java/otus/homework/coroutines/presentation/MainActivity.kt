package otus.homework.coroutines.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import otus.homework.coroutines.R
import otus.homework.coroutines.data.CatRepositoryImpl
import otus.homework.coroutines.di.DiContainer
import otus.homework.coroutines.domain.CatsPresenter
import otus.homework.coroutines.domain.viewModel.MainViewModel
import otus.homework.coroutines.domain.viewModel.Result
import java.lang.ref.WeakReference
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {

    private var catsPresenter: CatsPresenter? = null
    private lateinit var catsView: CatsView
    private val diContainer = DiContainer()
    private val factRepository = CatRepositoryImpl(diContainer.service)

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(factRepository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catsView = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
//        catsView.viewModel = viewModel
        setContentView(catsView)

        catsPresenter = CatsPresenter(factRepository, WeakReference(catsView))
        catsView.presenter = WeakReference(catsPresenter)
        catsPresenter?.onInitComplete()

//        viewModel.onInitComplete()
//        subscribeForResult()
    }

    private fun subscribeForResult() = lifecycleScope.launch {
        viewModel.result.collect { result ->
            catsView.apply {
                when (result) {
                    is Result.Success -> {
                        populate(result.catFact)
                    }

                    is Result.Error -> {
                        if (result.throwable is SocketTimeoutException) {
                            showTimeoutExceptionToast()
                        } else {
                            showToast(result.throwable.message ?: "Error")
                        }
                    }

                    Result.Idle -> {

                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        catsPresenter?.onStop()
        viewModel.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        catsPresenter?.detachView()
    }
}