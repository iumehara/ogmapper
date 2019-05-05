package io.umehara.ogmapper

import io.umehara.ogmapper.domain.OgDeterminer
import io.umehara.ogmapper.domain.OgDeterminer.*
import io.umehara.ogmapper.domain.OgStringTags
import io.umehara.ogmapper.domain.OgType
import io.umehara.ogmapper.domain.OgType.*
import io.umehara.ogmapper.service.HtmlFetcher
import io.umehara.ogmapper.service.OgStringTagParser
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.net.URL

class OgMapperTest {
    private val stubHtml = "<html></html>"
    private lateinit var ogStringTagParser: OgStringTagParser
    private lateinit var ogMapper: OgMapper

    @BeforeEach
    fun setUp() {
        val htmlFetcher = mock(HtmlFetcher::class.java)
        ogStringTagParser = mock(OgStringTagParser::class.java)
        ogMapper = OgMapper(htmlFetcher, ogStringTagParser)
        `when`(htmlFetcher.fetchHead(URL("http://www.example.com"))).thenReturn(stubHtml)
    }

    @Test
    fun process_validValues() {
        val ogStringTagsWithValidValues = OgStringTags(
            "hello",
            "WEBSITE",
            "http://example.com/image.png",
            "http://example.com",
            "http://example.com/audio.mp3",
            "detailed description",
            "auto",
            "en_US",
            "my site",
            "http://example.com/video.mp4"
        )
        `when`(ogStringTagParser.parse(stubHtml)).thenReturn(ogStringTagsWithValidValues)


        val ogTags = ogMapper.process(URL("http://www.example.com"))


        assertThat(ogTags?.title).isEqualTo("hello")
        assertThat(ogTags?.type).isEqualTo(WEBSITE)
        assertThat(ogTags?.image).isEqualTo(URL("http://example.com/image.png"))
        assertThat(ogTags?.url).isEqualTo(URL("http://example.com"))

        assertThat(ogTags?.audio).isEqualTo(URL("http://example.com/audio.mp3"))
        assertThat(ogTags?.description).isEqualTo("detailed description")
        assertThat(ogTags?.determiner).isEqualTo(AUTO)
        assertThat(ogTags?.locale).isEqualTo("en_US")
        assertThat(ogTags?.siteName).isEqualTo("my site")
        assertThat(ogTags?.video).isEqualTo(URL("http://example.com/video.mp4"))
    }

    @Test
    fun process_nullValues() {
        val ogStringTagsWithNullValues = OgStringTags()
        `when`(ogStringTagParser.parse(stubHtml)).thenReturn(ogStringTagsWithNullValues)


        val ogTags = ogMapper.process(URL("http://www.example.com"))


        assertThat(ogTags?.title).isEqualTo("www.example.com")
        assertThat(ogTags?.type).isEqualTo(WEBSITE)
        assertThat(ogTags?.image).isNull()
        assertThat(ogTags?.url).isEqualTo(URL("http://www.example.com"))

        assertThat(ogTags?.audio).isNull()
        assertThat(ogTags?.description).isNull()
        assertThat(ogTags?.determiner).isEqualTo(BLANK)
        assertThat(ogTags?.locale).isNull()
        assertThat(ogTags?.siteName).isNull()
        assertThat(ogTags?.video).isNull()
    }

    @Test
    fun process_emptyValues() {
        val ogStringTagsWithEmptyValues = OgStringTags("","","","","","","","","","")
        `when`(ogStringTagParser.parse(stubHtml)).thenReturn(ogStringTagsWithEmptyValues)


        val ogTags = ogMapper.process(URL("http://www.example.com"))


        assertThat(ogTags?.title).isEqualTo("")
        assertThat(ogTags?.type).isEqualTo(WEBSITE)
        assertThat(ogTags?.image).isNull()
        assertThat(ogTags?.url).isEqualTo(URL("http://www.example.com"))

        assertThat(ogTags?.audio).isNull()
        assertThat(ogTags?.description).isEqualTo("")
        assertThat(ogTags?.determiner).isEqualTo(BLANK)
        assertThat(ogTags?.locale).isEqualTo("")
        assertThat(ogTags?.siteName).isEqualTo("")
        assertThat(ogTags?.video).isNull()
    }

    @Test
    fun process_urlWithRelativePaths() {
        val ogStringTags = OgStringTags(
            url="/",
            image="/image.jpg",
            audio="/audio.mp3",
            video="/video.mp4"
        )

        `when`(ogStringTagParser.parse(stubHtml)).thenReturn(ogStringTags)
        val ogTags = ogMapper.process(URL("http://www.example.com"))

        assertThat(ogTags?.url).isEqualTo(URL("http://www.example.com/"))
        assertThat(ogTags?.image).isEqualTo(URL("http://www.example.com/image.jpg"))
        assertThat(ogTags?.audio).isEqualTo(URL("http://www.example.com/audio.mp3"))
        assertThat(ogTags?.video).isEqualTo(URL("http://www.example.com/video.mp4"))
    }

    @Test
    fun process_type() {
        assertTypeEnum(OgStringTags(type="ARTICLE"), ARTICLE)
        assertTypeEnum(OgStringTags(type="BOOK"), BOOK)
        assertTypeEnum(OgStringTags(type="MUSIC"), MUSIC)
        assertTypeEnum(OgStringTags(type="PROFILE"), PROFILE)
        assertTypeEnum(OgStringTags(type="VIDEO"), VIDEO)
        assertTypeEnum(OgStringTags(type="WEBSITE"), WEBSITE)

        assertTypeEnum(OgStringTags(), WEBSITE)
        assertTypeEnum(OgStringTags(type=""), WEBSITE)
        assertTypeEnum(OgStringTags(type="article"), ARTICLE)
    }

    private fun assertTypeEnum(ogStringTags: OgStringTags, expectedType: OgType) {
        `when`(ogStringTagParser.parse(stubHtml)).thenReturn(ogStringTags)
        val ogTags = ogMapper.process(URL("http://www.example.com"))
        assertThat(ogTags?.type).isEqualTo(expectedType)
    }

    @Test
    fun process_determiner() {
        assertDeterminerEnum(OgStringTags(determiner="A"), A)
        assertDeterminerEnum(OgStringTags(determiner="AN"), AN)
        assertDeterminerEnum(OgStringTags(determiner="AUTO"), AUTO)
        assertDeterminerEnum(OgStringTags(determiner="BLANK"), BLANK)
        assertDeterminerEnum(OgStringTags(determiner="THE"), THE)

        assertDeterminerEnum(OgStringTags(), BLANK)
        assertDeterminerEnum(OgStringTags(determiner=""), BLANK)
        assertDeterminerEnum(OgStringTags(determiner="the"), THE)
    }

    private fun assertDeterminerEnum(ogStringTags: OgStringTags, expectedType: OgDeterminer) {
        `when`(ogStringTagParser.parse(stubHtml)).thenReturn(ogStringTags)
        val ogTags = ogMapper.process(URL("http://www.example.com"))
        assertThat(ogTags?.determiner).isEqualTo(expectedType)
    }
}
