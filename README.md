# worker-pool

[![Tests](https://github.com/philiprehberger/kt-worker-pool/actions/workflows/publish.yml/badge.svg)](https://github.com/philiprehberger/kt-worker-pool/actions/workflows/publish.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.philiprehberger/worker-pool.svg)](https://central.sonatype.com/artifact/com.philiprehberger/worker-pool)
[![Last updated](https://img.shields.io/github/last-commit/philiprehberger/kt-worker-pool)](https://github.com/philiprehberger/kt-worker-pool/commits/main)

Coroutine-based worker pool for parallel batch processing with backpressure.

## Installation

### Gradle (Kotlin DSL)

```kotlin
implementation("com.philiprehberger:worker-pool:0.1.3")
```

### Maven

```xml
<dependency>
    <groupId>com.philiprehberger</groupId>
    <artifactId>worker-pool</artifactId>
    <version>0.1.3</version>
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

## Support

If you find this project useful:

⭐ [Star the repo](https://github.com/philiprehberger/kt-worker-pool)

🐛 [Report issues](https://github.com/philiprehberger/kt-worker-pool/issues?q=is%3Aissue+is%3Aopen+label%3Abug)

💡 [Suggest features](https://github.com/philiprehberger/kt-worker-pool/issues?q=is%3Aissue+is%3Aopen+label%3Aenhancement)

❤️ [Sponsor development](https://github.com/sponsors/philiprehberger)

🌐 [All Open Source Projects](https://philiprehberger.com/open-source-packages)

💻 [GitHub Profile](https://github.com/philiprehberger)

🔗 [LinkedIn Profile](https://www.linkedin.com/in/philiprehberger)

## License

[MIT](LICENSE)
