package com.fractalwrench.britishsummer

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

typealias TimeHandler = (defaultScheduler: Scheduler, tag: String) -> Scheduler

// time scheduling adapted from https://proandroiddev.com/testing-time-with-espresso-and-rxjava-e73c7496889

// TODO tidy up into own file, review whether API is best
/**
 * Scheduler used for time based operations.
 *
 *
 * During normal application runtime, the computation scheduler should be used.
 * But during the tests, there is possibility to override it to be able to better
 * test time based actions.
 */
object TimeScheduler {

    /**
     * Handler for time scheduler.
     */
    @Volatile
    var timeSchedulerHandler: TimeHandler? = null

    /**
     * Returns scheduler for time operations.
     *
     * @param tag Tag to be used in tests to mock the scheduler
     */
    fun time(tag: String): Scheduler {
        val handler = timeSchedulerHandler ?: return Schedulers.computation()
        return handler(Schedulers.computation(), tag)
    }

    /**
     * Resets current scheduler handler.
     */
    fun reset() {
        timeSchedulerHandler = null
    }
}

/**
 * Scheduler that instantly executes the task.
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