package io.umehara.ogmapper.service

import io.umehara.ogmapper.OgMapper

interface OgMapperFactory {
    fun build(): OgMapper
}
