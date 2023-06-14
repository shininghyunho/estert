package estert.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory

@Configuration
class SaxParserConfig {
    @Bean
    fun saxParser() : SAXParser {
        return saxParserFactory().newSAXParser()
    }

    @Bean
    fun saxParserFactory() : SAXParserFactory {
        return SAXParserFactory.newInstance()
    }
}