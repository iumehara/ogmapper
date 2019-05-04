package io.umehara.ogmapper.service

import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL

class HtmlFetcher {
    fun fetch(url: URL): Document? {
        val connection = Jsoup.connect(url.toString())
        connection.userAgent("Mozilla")
        connection.timeout(5000)
        return try {
            connection.get()
        } catch (e: HttpStatusException) {
            null
        }
    }
}
