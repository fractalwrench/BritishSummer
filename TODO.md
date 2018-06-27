## In progress

- Espresso tests, to test that the UI reacts to data correctly


## Backlog
- Dex/APK size count (blocked, broken on Android 8.0)
- Get location (with permission check, degrade to graceful behaviour)
- Save most recent/multiple locations (way in the future)
- Inject schedulers
- Organise gradle deps and separate version
- Create packages by feature
- Add 5 day weather forecast
- Test the model layer, view layer, and VM layer separately
- Fix ./gradlew check not running tests
- Parameterised Unit Tests to test unusual data
- Mockito on instrumentation tests (not sure working on P)

## Targets
- Regular app
- Instant App
- Wear App
- Auto App
- TV App
- App slice

# Completed!
- Static analysis: Android Lint, KtLint (./gradlew lint check)
- Obfuscation/minification: ProGuard
- CI: Travis
- Separate gradle files into logical sections (dependencies, etc)
- Dependency update check plugin: ./gradlew dependencyUpdates
- Bugsnag: measure application stability, errors vs crash rate
- Koin: _simple_ dependency injection with a Kotlin DSL. (Easier to understand than Dagger 2)
- Logging: Timber, only log in default builds with advanced Tree Of Souls technology
- Interceptor: adds query param of API key to each request
- OkHttp cache: reduce requests with cache-control and e-tags
- RxBinding/RxJava: reactive bindings for the android framework
- API keys: store in ~/.gradle/gradle.properties, add as environment variable on Travis
- Setup extra non-null observe extension method for livedata: https://proandroiddev.com/nonnull-livedata-with-kotlin-extension-26963ffd0333
- Log module (bugsnag breadcrumbs for non-fatal, notify for throwables, timber for debug)
- Wrap response with UiModel sealed class, with 4 different states. Essentially passes messages back to a very dumb UI
- Encapsulate debounce operator with extension
- Unit Tests + mocking with mockito, to avoid massive dependency graphs
