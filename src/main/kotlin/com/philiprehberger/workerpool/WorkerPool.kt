package com.philiprehberger.workerpool

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore

/** Process tasks in parallel with bounded concurrency. Returns results in submission order. */
public suspend fun <T, R> workerPool(
    concurrency: Int = 10,
    block: suspend WorkerPoolScope<T, R>.() -> Unit,
): List<R> {
    val scope = WorkerPoolScope<T, R>(concurrency)
    scope.block()
    return scope.collectResults()
}

public class WorkerPoolScope<T, R>(private val concurrency: Int) {
    private val tasks = mutableListOf<Pair<T, suspend (T) -> R>>()
    private var progressCallback: ((Int, Int) -> Unit)? = null
    private var errorCallback: ((T, Throwable) -> Unit)? = null

    /** Submit a task. */
    public fun submit(input: T, task: suspend (T) -> R) { tasks.add(input to task) }
    /** Set progress callback. */
    public fun onProgress(block: (completed: Int, total: Int) -> Unit) { progressCallback = block }
    /** Set error callback. */
    public fun onError(block: (T, Throwable) -> Unit) { errorCallback = block }

    internal suspend fun collectResults(): List<R> = coroutineScope {
        val semaphore = Semaphore(concurrency)
        var completed = 0
        tasks.map { (input, task) ->
            async {
                semaphore.acquire()
                try {
                    val result = task(input)
                    completed++
                    progressCallback?.invoke(completed, tasks.size)
                    result
                } catch (e: Throwable) {
                    errorCallback?.invoke(input, e)
                    throw e
                } finally {
                    semaphore.release()
                }
            }
        }.awaitAll()
    }
}
