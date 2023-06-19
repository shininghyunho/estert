package estert.domain.kakao

import estert.domain.api.kakao.config.KakaoProperties
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe

class KakaoConfigTest : StringSpec({
        "Kakao Yaml 불러오기" {
            val yaml = org.yaml.snakeyaml.Yaml(
                org.yaml.snakeyaml.constructor.Constructor(KakaoProperties::class.java))
            // resources\kakao.yml
            val inputStream = this.javaClass.classLoader
                .getResourceAsStream("kakao.yml")
            val kakaoProperties = yaml.load<KakaoProperties>(inputStream)

            println(kakaoProperties)
            println(kakaoProperties.kakao)
            println(kakaoProperties.kakao.apiKey)
            // should not empty
            kakaoProperties shouldNotBe null
        }
})