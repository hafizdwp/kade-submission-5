package me.hafizdwp.kade_submission_5.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

abstract class BaseFragment<T: AppCompatActivity> : Fragment() {

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun onExtractArguments()
    abstract fun onReady()

    lateinit var mContext: Context
    lateinit var mActivity: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity() as T
        onExtractArguments()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onReady()
    }

    fun <T> LiveData<T>.observe(
            action: (T?) -> Unit) {
        observe(viewLifecycleOwner, Observer {
            action.invoke(it)
        })
    }
}
