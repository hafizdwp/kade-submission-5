package me.hafizdwp.kade_submission_5.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import me.hafizdwp.kade_submission_5.R

/**
 * @author hafizdwp
 * 30/11/2019
 **/
abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun onExtractIntents()
    abstract fun onReady()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)

        onExtractIntents()
        onReady()
    }

    fun <T> LiveData<T>.observe(action: (T?) -> Unit) {
        observe(this@BaseActivity, Observer {
            action(it)
        })
    }

    /**
     * Snackbars
     * ---------------------------------------------------------------------------------------------
     * */

    var mSnackbar: Snackbar? = null

    fun snack(@StringRes stringRes: Int, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit = {
        action(getString(R.string.ok)) {
            dismiss()
        }
    }) {
        snack(getString(stringRes), length, action)
    }

    fun snack(string: String, length: Int = Snackbar.LENGTH_LONG, action: Snackbar.() -> Unit = {
        action(getString(R.string.ok)) {
            dismiss()
        }
    }) {
        mSnackbar = Snackbar.make(window.decorView.rootView, string, length)
        mSnackbar?.action()
        mSnackbar?.show()
    }

    fun Snackbar.action(@StringRes stringRes: Int, color: Int? = null, listener: (View) -> Unit) {
        action(stringRes, color, listener)
    }

    fun Snackbar.action(string: String, color: Int? = null, listener: (View) -> Unit) {
        setAction(string, listener)
        color?.let { setActionTextColor(color) }
    }
}