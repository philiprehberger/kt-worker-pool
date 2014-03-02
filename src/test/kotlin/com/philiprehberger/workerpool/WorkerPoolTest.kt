package com.philiprehberger.workerpool

import kotlinx.coroutines.test.runTest
import kotlin.test.*

class WorkerPoolTest {
    @Test fun `basic processing`() = runTest {
        val results = workerPool<Int, String>(concurrency = 2) {
            submit(1) { "result-$it" }
            submit(2) { "result-$it" }
            submit(3) { "result-$it" }
        }
        assertEquals(listOf("result-1", "result-2", "result-3"), results)
    }
    @Test fun `parallelMap preserves order`() = runTest {
        val results = listOf(3, 1, 2).parallelMap(concurrency = 2) { it * 10 }
        assertEquals(listOf(30, 10, 20), results)
    }
}
