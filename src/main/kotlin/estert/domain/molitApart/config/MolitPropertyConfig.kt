package estert.domain.molitApart.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.InputStream

@Configuration
class MolitConfig {
    @Bean
    fun molitProperty() : MolitProperty {
        return molitProperties().molit
    }

    private fun molitProperties() : MolitProperties {
        return Yaml(Constructor(MolitProperties::class.java))
            .load(inputStream())
    }

    private fun inputStream() : InputStream {
        return this.javaClass
            .classLoader
            .getResourceAsStream("molit.yml")!!
    }
}