package io.umehara.ogmapper.jsoup

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.net.URL

class JsoupHtmlFetcherTest {
    @Test
    fun fetchHead_networkTest() {
        val htmlFetcher = JsoupHtmlFetcher()

        val fetchedDocument = htmlFetcher.fetchHead(URL("http://www.example.com"))!!


        assertThat(fetchedDocument::class.java).isEqualTo(String::class.java)
    }
}
