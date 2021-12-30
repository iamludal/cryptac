package fr.tac.cryptac.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import fr.tac.cryptac.R
import fr.tac.cryptac.databinding.ActivityDetailsBinding
import fr.tac.cryptac.viewmodel.DetailsViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

private val TAG = DetailsActivity::class.simpleName

const val SYMBOL = "SYMBOL"

class DetailsActivity : AppCompatActivity() {

    /**
     * Create instances when they are first called
     * @see {https://kotlinlang.org/docs/delegated-properties.html#lazy-properties}
     */
    private val viewModel by lazy { DetailsViewModel(application) }
    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }
    private val retry: Button by lazy { binding.error.findViewById(R.id.retry) }

    private lateinit var disposable: Disposable

    /**
     * Setup the activity
     */
    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(binding.root)
        enableToolbar()
        val symbol = intent.getStringExtra(SYMBOL) ?: error("missing symbol extra")
        loadDetails(symbol)
        retry.setOnClickListener { loadDetails(symbol) }
    }

    /**
     * Load the crypto details and update the model once the data is loaded. Display an error if
     * the loading failed.
     * @param symbol the crypto symbol
     */
    private fun loadDetails(symbol: String) {
        binding.spinner.visibility = View.VISIBLE
        binding.error.visibility = View.GONE
        binding.list.visibility = View.GONE

        disposable = viewModel.getCryptoDetails(symbol)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ crypto ->
                binding.model = crypto
                binding.website.setOnClickListener { openLink(crypto.website) }
                binding.github.setOnClickListener { openLink(crypto.sourceCode) }
                binding.documentation.setOnClickListener { openLink(crypto.technicalDoc) }
                binding.twitter.setOnClickListener { openLink(crypto.twitter) }
                binding.reddit.setOnClickListener { openLink(crypto.reddit) }
                binding.spinner.visibility = View.GONE
                binding.list.visibility = View.VISIBLE
            }, {
                Log.e(TAG, "Could not get crypto details: $it")
                binding.error.visibility = View.VISIBLE
                binding.spinner.visibility = View.GONE
            })
    }

    /**
     * Enable the toolbar
     */
    private fun enableToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * When the toolbar back arrow is pressed, go back to the main activity
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /**
     * Inflate the toolbar in the menu
     * @param menu the menu where to inflate the toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Dispose from the running observables when the activity is destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    /**
     * Create an intent to open a link to the browser
     * @param link the link to open
     */
    private fun openLink(link: String?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}