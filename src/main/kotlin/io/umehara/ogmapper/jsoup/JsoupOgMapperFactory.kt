package io.umehara.ogmapper.jsoup

import io.umehara.ogmapper.OgMapper
import io.umehara.ogmapper.service.OgMapperFactory

/**
 * Factory for building an OgMapper instance with JSOUP dependencies.
 * Call the process method on the OgMapper with a url argument, and receive
 * back an OgTags object with og tag values.
 *
 * See the [Open Graph Protocol spec](http://ogp.me/)
 *
 * @author Ichizo Umehara
 */
class JsoupOgMapperFactory: OgMapperFactory {
    override fun build(): OgMapper {
        val htmlFetcher = JsoupHtmlFetcher()
        val ogStringTagParser = JsoupOgStringTagParser()
        return OgMapper(htmlFetcher, ogStringTagParser)
    }
}
