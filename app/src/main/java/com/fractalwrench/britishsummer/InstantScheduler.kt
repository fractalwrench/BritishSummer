package com.fractalwrench.britishsummer

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import java.util.concurrent.TimeUnit

/**
 * Scheduler that instantly executes the task.
 *
 * Adapted from https://proandroiddev.com/testing-time-with-espresso-and-rxjava-e73c7496889
 */
object InstantScheduler : Scheduler() {
    override fun createWorker(): Worker =
        object : Worker() {
            override fun isDisposed(): Boolean = false

            override fun dispose() = Unit

            override fun schedule(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                run.run()
                return Disposables.empty()
            }
        }
}