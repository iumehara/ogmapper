package io.umehara.ogmapper.jsoup

import io.umehara.ogmapper.DefaultOgMapper
import io.umehara.ogmapper.service.OgMapperFactory

/**
 * Factory for building an DefaultOgMapper instance with JSOUP dependencies.
 * Call the process method on the DefaultOgMapper with a url argument, and receive
 * back an OgTags object with og tag values.
 *
 * See the [Open Graph Protocol spec](http://ogp.me/)
 *
 * @author Ichizo Umehara
 */
class JsoupOgMapperFactory: OgMapperFactory {
    override fun build(): DefaultOgMapper {
        val htmlFetcher = JsoupHtmlFetcher()
        val ogStringTagParser = JsoupOgStringTagParser()
        return DefaultOgMapper(htmlFetcher, ogStringTagParser)
    }
}
