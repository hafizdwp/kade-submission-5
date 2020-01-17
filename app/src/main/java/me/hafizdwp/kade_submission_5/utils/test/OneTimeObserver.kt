package me.hafizdwp.kade_submission_5.utils.test

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer

class OneTimeObserver<T>(private val todo: (T) -> Any?) : Observer<T>, LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    init {
        // Important, to be able to demonstrate the lifecycle
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun onChanged(t: T) {

        todo(t)

        // Destroy lifecycle to stop observing
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}