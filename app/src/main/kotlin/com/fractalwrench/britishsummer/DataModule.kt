import com.fractalwrench.britishsummer.CurrentWeatherRepository
import org.koin.dsl.module.module

val dataModule = module {
    bean { CurrentWeatherRepository(get()) }
}
