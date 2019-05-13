package io.umehara.ogmapper

import io.umehara.ogmapper.domain.OgTags
import java.net.URL

/**
 * The interface for the main class that parses og tags from websites.
 * Call the process method with a url argument, and receive
 * back an OgTags object with og tag values.
 *
 * See the [Open Graph Protocol spec](http://ogp.me/)
 *
 * @author Ichizo Umehara
 */
interface OgMapper {
    fun process(urlToProcess: URL): OgTags?
}
