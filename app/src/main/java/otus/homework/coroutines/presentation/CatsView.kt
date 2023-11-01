package otus.homework.coroutines.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import otus.homework.coroutines.domain.CatsPresenter
import otus.homework.coroutines.R
import otus.homework.coroutines.domain.views.ICatsView
import otus.homework.coroutines.domain.models.CatFact
import otus.homework.coroutines.domain.viewModel.MainViewModel
import java.lang.ref.WeakReference

class CatsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ICatsView {

    var presenter: WeakReference<CatsPresenter>? = null
    var viewModel: MainViewModel? = null
    private var fact: TextView? = null
    private var image: ImageView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
        fact = findViewById(R.id.fact_textView)
        image = findViewById(R.id.cat_image)
        findViewById<Button>(R.id.button).setOnClickListener {
            presenter?.get()?.onInitComplete()
            viewModel?.onInitComplete()
        }
    }

    override fun populate(fact: CatFact) {
        this.fact?.text = fact.fact
        Picasso.get()
            .load(fact.imageUrl)
            .placeholder(R.drawable.baseline_access_time_24)
            .error(R.drawable.baseline_error_24)
            .into(image)
    }

    override fun showTimeoutExceptionToast() {
        Toast.makeText(
            context,
            context.resources.getString(R.string.socket_timeout_exception),
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}