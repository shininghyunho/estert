package estert.domain.api.kakao.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor
import java.io.InputStream

@Configuration
class KakaoPropertyConfig {
    @Bean
    fun kakaoProperty() : KakaoProperty {
        return kakaoProperties().kakao
    }

    private fun kakaoProperties() : KakaoProperties {
        return Yaml(Constructor(KakaoProperties::class.java))
            .load(inputStream())
    }

    private fun inputStream() : InputStream {
        return this.javaClass
            .classLoader
            .getResourceAsStream("api/application-kakao.yml")!!
    }
}