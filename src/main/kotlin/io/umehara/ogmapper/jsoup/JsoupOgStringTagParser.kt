package io.umehara.ogmapper.jsoup

import io.umehara.ogmapper.domain.OgStringTags
import io.umehara.ogmapper.service.OgStringTagParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class JsoupOgStringTagParser: OgStringTagParser {
    override fun parse(html: String): OgStringTags? {
        val document = Jsoup.parse(html)
        val title: String? = findTag(document, "title")
        val type: String? = findTag(document, "type")
        val image: String? = findTag(document, "image")
        val url: String? = findTag(document, "url")
        val audio: String? = findTag(document, "audio")
        val description: String? = findTag(document, "description")
        val determiner: String? = findTag(document, "determiner")
        val locale: String? = findTag(document, "locale")
        val siteName: String? = findTag(document, "site_name")
        val video: String? = findTag(document, "video")

        if (title == null &&
            type == null &&
            image == null &&
            url == null &&
            audio == null &&
            description == null &&
            determiner == null &&
            locale == null &&
            siteName == null &&
            video == null
        ) {
            return null
        }


        return OgStringTags(
            title,
            type,
            image,
            url,
            audio,
            description,
            determiner,
            locale,
            siteName,
            video
        )
    }

    private fun findTag(document: Document, property: String): String? {
        var tag: String? = null
        val ogTag = document.select("meta[property='og:$property']")
        if (ogTag != null && ogTag.size >= 1) {
            tag = ogTag.first().attr("content")
        }
        return tag
    }
}
