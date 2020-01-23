package me.hafizdwp.kade_submission_5.utils.extentions

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hafizdwp.kade_submission_5.base.BaseResponse
import me.hafizdwp.kade_submission_5.utils.ResultState
import me.hafizdwp.kade_submission_5.utils.ViewModelFactory
import me.hafizdwp.kade_submission_5.utils.test.OneTimeObserver
import kotlin.coroutines.CoroutineContext


/**
 * @author hafizdwp
 * 13/11/2019
 **/

fun Context.myCircularProgressDrawable(): androidx.swiperefreshlayout.widget.CircularProgressDrawable =
        androidx.swiperefreshlayout.widget.CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

fun <T : Drawable> RequestBuilder<T>.withLoadingPlaceholder(context: Context): RequestBuilder<T> {
    return this.apply(
            RequestOptions().placeholder(context.myCircularProgressDrawable()))
}

fun <VM : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<VM>) =
        ViewModelProviders.of(this,
                ViewModelFactory.getInstance(application)
        ).get(viewModelClass)

fun <VM : ViewModel> androidx.fragment.app.Fragment.obtainViewModel(viewModelClass: Class<VM>) =
        ViewModelProviders.of(requireActivity(),
                ViewModelFactory.getInstance(
                        requireActivity().application
                )
        ).get(viewModelClass)

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel() = ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(T::class.java)

inline fun <reified VM : ViewModel> androidx.fragment.app.Fragment.obtainViewModel() =
        ViewModelProviders.of(requireActivity(),
                ViewModelFactory.getInstance(
                        requireActivity().application
                )
        ).get(VM::class.java)

/**
 * Log ext
 * */
fun log(msg: String,
        tag: String? = null) {
    Log.d(tag ?: "mytag", msg)
}

fun logError(msg: String?,
             tag: String? = null) {
    msg?.let {
        Log.e(tag ?: "mytag", msg)
    }
}

/**
 * Gson ext
 * ---------------------------------------------------------------------------------------------
 * */
val gson by lazy { Gson() }

inline fun <reified T> makeType() = object : TypeToken<T>() {}.type

fun <T> T.toJson(): String = gson.toJson(this).trim()

inline fun <reified T> String.fromJson(): T = gson.fromJson(this,
        makeType<T>()
)

/**
 * LiveData ext
 * ---------------------------------------------------------------------------------------------
 * */

fun <T> MutableLiveData<T>.call() {
    value = null
}

fun <T> MutableLiveData<T>.postCall() {
    postValue(null)
}

fun <T> LiveData<T>.observeOnce(todo: (T) -> Any?) {
    val oneTimeObserver = OneTimeObserver(todo = todo)
    observe(oneTimeObserver, oneTimeObserver)
}

/**
 * Coroutine ext
 * ---------------------------------------------------------------------------------------------
 * */

fun launchIO(todo: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        todo()
    }
}

fun launchMain(todo: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(Dispatchers.Main).launch {
        todo()
    }
}

suspend fun onMain(todo: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.Main) {
        todo()
    }
}

suspend fun <T> asyncAwait(context: CoroutineContext = Dispatchers.IO,
                           action: suspend CoroutineScope.() -> T): T =
        CoroutineScope(context).async { action(this) }.await()

inline fun <reified T> getTag(): String {
    return T::class.java.simpleName
}

suspend fun <T : Any> fetchLeagues(call: suspend () -> BaseResponse<T>): ResultState<T> {
    return try {
        val response = asyncAwait { call() }
        ResultState.Success(response.leagues!!)
    } catch (e: Exception) {
        ResultState.Error(e.message)
    }
}

suspend fun <T : Any> fetchEvents(call: suspend () -> BaseResponse<T>): ResultState<T> {
    return try {
        val response = asyncAwait { call() }
        ResultState.Success(response.events!!)
    } catch (e: Exception) {
        ResultState.Error(e.message)
    }
}

suspend fun <T : Any> fetchEvent(call: suspend () -> BaseResponse<T>): ResultState<T> {
    return try {
        val response = asyncAwait { call() }
        ResultState.Success(response.event!!)
    } catch (e: Exception) {
        ResultState.Error(e.message)
    }
}

suspend fun <T : Any> fetchTeam(call: suspend () -> BaseResponse<T>): ResultState<T> {
    return try {
        val response = asyncAwait { call() }
        ResultState.Success(response.teams!!)
    } catch (e: Exception) {
        ResultState.Error(e.message)
    }
}

suspend fun <T : Any> fetchTable(call: suspend () -> BaseResponse<T>): ResultState<T> {
    return try {
        val response = asyncAwait { call() }
        ResultState.Success(response.table!!)
    } catch (e: Exception) {
        ResultState.Error(e.message)
    }
}

/**
 * Ez toast
 * */
var mToast: Toast? = null

fun AppCompatActivity.toast(msg: String) {
    if (msg.isNotBlank()) {
        mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        mToast?.show()
    }
}

fun AppCompatActivity.toastSpammable(msg: String?) {
    if (msg != null) {
        if (msg.isNotBlank()) {
            if (mToast != null) mToast?.cancel()
            mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
            mToast?.show()
        }
    }
}

fun Fragment.toast(msg: String) {
    (requireActivity() as? AppCompatActivity)?.toast(msg)
}

fun Fragment.toastSpammable(msg: String?) {
    (requireActivity() as? AppCompatActivity)?.toastSpammable(msg)
}

fun <T : Fragment> T.withArgs(bundle: Bundle.() -> Unit): Fragment {
    return this.apply {
        arguments = Bundle().apply(bundle)
    }
}

/**
 * View visibility utility
 * */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

/**
 * Dimensions util
 * ---------------------------------------------------------------------------------------------
 * */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)

val Int.sp: Int
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity * 0.5f).toInt()

val Float.sp: Float
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity * 0.5f)

fun largeLog(tag: String, content: String) {
    if (content.length > 4000) {
        Log.d(tag, content.substring(0, 4000))
        largeLog(tag, content.substring(4000))
    } else {
        Log.d(tag, content)
    }
}

fun TabLayout.disableClickTablayout(){
    for (i in 0 until this.tabCount){
        (this.getChildAt(0) as ViewGroup).getChildAt(i).isEnabled = false
    }
}