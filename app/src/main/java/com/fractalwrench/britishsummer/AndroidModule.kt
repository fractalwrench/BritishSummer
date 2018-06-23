import org.koin.dsl.module.applicationContext

val androidModule = applicationContext {
    bean { this }
}
