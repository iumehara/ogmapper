package io.umehara.ogmapper.domain

import java.net.URL

/**
 * Object that is returned from the OgMapper process method.
 * fields are based on those defined in the Open Graph Protocol
 *
 * See the [Open Graph Protocol spec](http://ogp.me/)
 *
 * @author Ichizo Umehara
 */
data class OgTags(
    val title: String,
    val type: OgType,
    val url: URL,
    val image: URL? = null,
    val audio: URL? = null,
    val description: String? = null,
    val determiner: OgDeterminer? = null,
    val locale: String? = null,
    val siteName: String? = null,
    val video: URL? = null
)
