package io.umehara.ogmapper.service

import io.umehara.ogmapper.domain.OgStringTags
/**
 * A dependency of the DefaultOgMapper.
 * Given an HTML string, will parse it for Open Graph tags.
 *
 * See the [Open Graph Protocol spec](http://ogp.me/)
 *
 * @author Ichizo Umehara
 */
interface OgStringTagParser {
    fun parse(html: String): OgStringTags?
}
