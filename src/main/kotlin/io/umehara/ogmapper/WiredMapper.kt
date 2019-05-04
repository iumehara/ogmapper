package io.umehara.ogmapper

import io.umehara.ogmapper.service.HtmlFetcher
import io.umehara.ogmapper.service.OgStringTagParser

class OgMapperFactory {
    fun build(): OgMapper {
        val htmlFetcher = HtmlFetcher()
        val ogStringTagParser = OgStringTagParser()
        return OgMapper(htmlFetcher, ogStringTagParser)
    }
}
