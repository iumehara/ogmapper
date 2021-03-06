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

/**
 * The default implementation of the OgMapper implementation.
 * A class for parsing og tags from websites.
 * Call the process method with a url argument, and receive
 * back an OgTags object with og tag values.
 *
 * See the [Open Graph Protocol spec](http://ogp.me/)
 *
 * @author Ichizo Umehara
 */
class DefaultOgMapper(private val htmlFetcher: HtmlFetcher,
                      private val ogStringTagParser: OgStringTagParser): OgMapper {
    override fun process(urlToProcess: URL): OgTags? {
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

    private fun simplifyTag(maybeComplexTag: String): String {
        val tag = maybeComplexTag.toUpperCase()
        return tag.split(".").first()
    }

    private fun formatType(maybeType: String?): OgType {
        if (maybeType == null) return OgType.WEBSITE

        var type = OgType.WEBSITE
        try {
            type = OgType.valueOf(simplifyTag(maybeType))
        } catch (e: IllegalArgumentException) {}

        return type
    }

    private fun formatDeterminer(maybeDeterminer: String?): OgDeterminer {
        if (maybeDeterminer == null) return BLANK

        var determiner = BLANK
        try {
            determiner = valueOf(simplifyTag(maybeDeterminer))
        } catch (e: IllegalArgumentException) {}

        return determiner
    }
}
