package io.umehara.ogmapper.jsoup

import io.umehara.ogmapper.service.HtmlFetcher
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import java.net.URL

class JsoupHtmlFetcher: HtmlFetcher {
    override fun fetchHead(url: URL): String? {
        val connection = Jsoup.connect(url.toString())
        connection.userAgent("Mozilla")
        connection.timeout(5000)
        return try {
            connection.get().head().html()
        } catch (e: HttpStatusException) {
            null
        }
    }
}
