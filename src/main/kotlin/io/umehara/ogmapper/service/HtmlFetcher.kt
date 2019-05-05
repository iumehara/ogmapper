package io.umehara.ogmapper.service

import java.net.URL

interface HtmlFetcher {
    fun fetchHead(url: URL): String?
}
