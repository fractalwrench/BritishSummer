import com.fractalwrench.britishsummer.CurrentWeatherRepository
import org.koin.dsl.module.applicationContext

val dataModule = applicationContext {
    bean { CurrentWeatherRepository(get()) }
}
