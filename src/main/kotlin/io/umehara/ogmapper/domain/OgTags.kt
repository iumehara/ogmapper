package io.umehara.ogmapper.domain

import java.net.URL

data class OgTags(
    val title: String,
    val type: OgType,
    val url: URL,
    val image: URL?,
    val audio: URL?,
    val description: String?,
    val determiner: OgDeterminer?,
    val locale: String?,
    val siteName: String?,
    val video: URL?
)
