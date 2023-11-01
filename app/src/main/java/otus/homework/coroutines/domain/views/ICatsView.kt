package otus.homework.coroutines.domain.views;

import otus.homework.coroutines.domain.models.CatFact

interface ICatsView {

    fun populate(fact: CatFact)

    fun showTimeoutExceptionToast()

    fun showToast(message: String)
}
