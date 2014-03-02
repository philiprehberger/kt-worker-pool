package com.philiprehberger.workerpool

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore

/** Map a list in parallel with bounded concurrency. Results are in the same order as the input. */
public suspend fun <T, R> List<T>.parallelMap(concurrency: Int = 10, transform: suspend (T) -> R): List<R> = coroutineScope {
    val semaphore = Semaphore(concurrency)
    map { item ->
        async {
            semaphore.acquire()
            try { transform(item) } finally { semaphore.release() }
        }
    }.awaitAll()
}
