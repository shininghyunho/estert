package estert.domain.api.vworld.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.InputStream

@Configuration
class VWorldPropertyConfig {
    @Bean
    fun vWorldProperty() : VWorldProperty {
        return vworldProperties().vworld
    }

    private fun vworldProperties() : VWorldProperties {
        return Yaml(Constructor(VWorldProperties::class.java))
            .load(inputStream())
    }

    private fun inputStream() : InputStream {
        return this.javaClass
            .classLoader
            .getResourceAsStream("api/application-vworld.yml")!!
    }
}