package io.umehara.ogmapper.service

import io.umehara.ogmapper.domain.OgStringTags

interface OgStringTagParser {
    fun parse(html: String): OgStringTags?
}
