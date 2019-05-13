package io.umehara.ogmapper.service

import java.net.URL

/**
 * A dependency of the DefaultOgMapper.
 * Given a URL, will fetch the corresponding HTML
 * and return it as a string.
 *
 * @author Ichizo Umehara
 */
interface HtmlFetcher {
    fun fetchHead(url: URL): String?
}
