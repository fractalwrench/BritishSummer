## In progress



## Backlog

- Signing
- Android Jetpack
- Timber
- RxKotlin
- LiveData/data binding
- Testing + mocking
- Auto update badges
- Dex/APK size count (blocked, broken on Android 8.0)

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



API keys: store in ~/.gradle/gradle.properties, add as environment variable on Travis