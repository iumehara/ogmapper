package io.umehara.ogmapper.jsoup

import io.umehara.ogmapper.OgMapper
import io.umehara.ogmapper.service.OgMapperFactory

class JsoupOgMapperFactory: OgMapperFactory {
    override fun build(): OgMapper {
        val htmlFetcher = JsoupHtmlFetcher()
        val ogStringTagParser = JsoupOgStringTagParser()
        return OgMapper(htmlFetcher, ogStringTagParser)
    }
}
