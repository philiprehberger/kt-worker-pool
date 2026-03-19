# worker-pool

[![CI](https://github.com/philiprehberger/kt-worker-pool/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-worker-pool/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/worker-pool)](https://central.sonatype.com/artifact/com.philiprehberger/worker-pool)

Coroutine-based worker pool for parallel batch processing with backpressure.

## Requirements

- Kotlin 1.9+ / Java 17+

## Installation

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("com.philiprehberger:worker-pool:0.1.0")
}
```

### Maven

```xml
<dependency>
    <groupId>com.philiprehberger</groupId>
    <artifactId>worker-pool</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

```kotlin
import com.philiprehberger.workerpool.*

val results = workerPool<String, Int>(concurrency = 5) {
    urls.forEach { url -> submit(url) { fetchSize(it) } }
    onProgress { done, total -> println("$done/$total") }
}

// Simple list parallel map
val sizes = urls.parallelMap(concurrency = 10) { fetchSize(it) }
```

## API

| Function / Class | Description |
|------------------|-------------|
| `workerPool(concurrency) { }` | Process tasks with bounded parallelism |
| `WorkerPoolScope.submit(input, task)` | Submit a task |
| `WorkerPoolScope.onProgress { completed, total -> }` | Progress callback |
| `List<T>.parallelMap(concurrency, transform)` | Parallel list mapping |

## Development

```bash
./gradlew test
./gradlew build
```

## License

MIT
