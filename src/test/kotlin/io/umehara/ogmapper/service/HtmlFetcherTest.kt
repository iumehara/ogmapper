package io.umehara.ogmapper.service

import org.assertj.core.api.Assertions.assertThat
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Test
import java.net.URL

class HtmlFetcherTest {
    @Test
    fun htmlFetcher_networkTest() {
        val htmlFetcher = HtmlFetcher()

        val fetchedDocument = htmlFetcher.fetch(URL("http://www.example.com"))!!


        assertThat(fetchedDocument::class.java).isEqualTo(Document::class.java)
    }
}
