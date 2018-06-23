package com.fractalwrench.britishsummer

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

// adapted from https://proandroiddev.com/nonnull-livedata-with-kotlin-extension-26963ffd0333

/**
 * Observes a nullable item and emits an event if it is non-null.
 */
fun <T> LiveData<T>.nonNullObserve(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}
