package estert.domain.vWorld

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe

class VWorldConfigTest : StringSpec({
    "VWorld Yaml 불러오기" {
        val yaml = org.yaml.snakeyaml.Yaml(
            org.yaml.snakeyaml.constructor.Constructor(estert.domain.vworld.config.VWorldProperties::class.java))
        // resources\vworld.yml
        val inputStream = this.javaClass.classLoader
            .getResourceAsStream("vworld.yml")
        val vworldProperties = yaml.load<estert.domain.vworld.config.VWorldProperties>(inputStream)

        println(vworldProperties)
        println(vworldProperties.vworld)
        println(vworldProperties.vworld.apiKey)
        println(vworldProperties.vworld.domain)
        // should not empty
        vworldProperties shouldNotBe null
    }
})