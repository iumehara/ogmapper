package io.umehara.ogmapper

import io.umehara.ogmapper.domain.OgDeterminer
import io.umehara.ogmapper.domain.OgDeterminer.BLANK
import io.umehara.ogmapper.domain.OgDeterminer.valueOf
import io.umehara.ogmapper.domain.OgTags
import io.umehara.ogmapper.domain.OgType
import io.umehara.ogmapper.service.HtmlFetcher
import io.umehara.ogmapper.service.OgStringTagParser
import java.net.MalformedURLException
import java.net.URL


class OgMapper(private val htmlFetcher: HtmlFetcher,
               private val ogStringTagParser: OgStringTagParser) {
    fun process(urlToProcess: URL): OgTags? {
        val document = htmlFetcher.fetchHead(urlToProcess) ?: return null
        val ogStringTags = ogStringTagParser.parse(document) ?: return null

        val title = ogStringTags.title ?: urlToProcess.host
        val type = formatType(ogStringTags.type)
        val url = formatUrl(urlToProcess, ogStringTags.url) ?: urlToProcess
        val image = formatUrl(url, ogStringTags.image)
        val audio = formatUrl(url, ogStringTags.audio)
        val description = ogStringTags.description
        val determiner = formatDeterminer(ogStringTags.determiner)
        val locale = ogStringTags.locale
        val siteName = ogStringTags.siteName
        val video = formatUrl(url, ogStringTags.video)

        return OgTags(
            title,
            type,
            url,
            image,
            audio,
            description,
            determiner,
            locale,
            siteName,
            video
        )
    }

    private fun formatUrl(baseUrl: URL, ogImageUrl: String?): URL? {
        if (ogImageUrl == null || ogImageUrl.isEmpty()) return null
        try {
            return URL(ogImageUrl)
        } catch (e: MalformedURLException) {
            if (ogImageUrl.first().toString() == "/") {
                val base = baseUrl.protocol + "://" + baseUrl.host
                return URL(base + ogImageUrl)
            }

            return null
        }
    }

    private fun formatType(maybeType: String?): OgType {
        if (maybeType == null) return OgType.WEBSITE

        var type = OgType.WEBSITE
        try {
            type = OgType.valueOf(maybeType.toUpperCase())
        } catch (e: IllegalArgumentException) {}

        return type
    }

    private fun formatDeterminer(maybeDeterminer: String?): OgDeterminer {
        if (maybeDeterminer == null) return BLANK

        var determiner = BLANK
        try {
            determiner = valueOf(maybeDeterminer.toUpperCase())
        } catch (e: IllegalArgumentException) {}

        return determiner
    }
}
