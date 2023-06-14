package estert.domain.molitApart

import estert.domain.molitApart.config.MolitConfig
import estert.domain.molitApart.config.MolitProperties
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe
import org.yaml.snakeyaml.Yaml

class MolitApartConfigTest : StringSpec({

    "Molit Yaml 불러오기" {
        val yaml = Yaml(
            org.yaml.snakeyaml.constructor.Constructor(MolitProperties::class.java))
        // resources\molit.yml
        val inputStream = this.javaClass.classLoader
            .getResourceAsStream("molit.yml")
        val molitProperties = yaml.load<MolitProperties>(inputStream)

        println(molitProperties)
        println(molitProperties.molit)
        println(molitProperties.molit.apiKeyList)
        // should not empty
        molitProperties shouldNotBe null
    }
})