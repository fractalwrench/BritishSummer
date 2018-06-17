## In progress

- Wrap response with Response/NetworkBoundResponse class
- Begin binding data


## Backlog

- LiveData/data binding according to architecture
- Testing + mocking
- Dex/APK size count (blocked, broken on Android 8.0)
- Get location (with permission check, degrade to graceful behaviour)
- Save multiple locations (way in the future)

## Targets

- Regular app
- Instant App
- Wear App
- Auto App
- TV App
- App slice


# Completed!

Static analysis: Android Lint, KtLint (./gradlew lint check)
Obfuscation/minification: ProGuard
CI: Travis
Separate gradle files into logical sections (dependencies, etc)
Dependency update check plugin: ./gradlew dependencyUpdates
Bugsnag: measure application stability, errors vs crash rate
Dagger: dependency injection (keep your sanity)
Logging: Timber, only log in default builds with advanced Tree Of Souls technology
Interceptor: adds query param of API key to each request
OkHttp cache: reduce requests with cache-control and e-tags
RxBinding/RxJava: reactive bindings for the android framework

API keys: store in ~/.gradle/gradle.properties, add as environment variable on Travis