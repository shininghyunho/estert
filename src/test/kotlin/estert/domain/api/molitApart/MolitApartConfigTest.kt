package estert.domain.api.molitApart

import estert.domain.api.molitApart.config.MolitProperties
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe
import org.yaml.snakeyaml.Yaml

class MolitApartConfigTest : StringSpec({

    "Molit Yaml 불러오기" {
        val yaml = Yaml(
            org.yaml.snakeyaml.constructor.Constructor(MolitProperties::class.java))
        // resources\application-application-molit.yml
        val inputStream = this.javaClass.classLoader
            .getResourceAsStream("api/application-molit.yml")
        val molitProperties = yaml.load<MolitProperties>(inputStream)

        println(molitProperties)
        println(molitProperties.molit)
        println(molitProperties.molit.apiKeyList)
        // should not empty
        molitProperties shouldNotBe null
    }
})