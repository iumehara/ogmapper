package io.umehara.ogmapper.service

import io.umehara.ogmapper.DefaultOgMapper

interface OgMapperFactory {
    fun build(): DefaultOgMapper
}
