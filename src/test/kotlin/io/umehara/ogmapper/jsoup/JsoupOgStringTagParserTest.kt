package io.umehara.ogmapper.jsoup

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.io.File
import kotlin.text.Charsets.UTF_8

class JsoupOgStringTagParserTest {
    private lateinit var jsoupOgTagParser: JsoupOgStringTagParser

    @Before
    internal fun setUp() {
        jsoupOgTagParser = JsoupOgStringTagParser()
    }

    @Test
    fun parse_empty() {
        val html = File("src/test/resources/html/empty.html").readText(UTF_8)


        val ogTags = jsoupOgTagParser.parse(html)


        assertThat(ogTags).isNull()
    }

    @Test
    fun parse_complete() {
        val html = File("src/test/resources/html/complete.html").readText(UTF_8)


        val ogTags = jsoupOgTagParser.parse(html)


        assertThat(ogTags?.title).isEqualTo("The Title")
        assertThat(ogTags?.type).isEqualTo("website")
        assertThat(ogTags?.image).isEqualTo("http://www.example.com/image.jpg")
        assertThat(ogTags?.url).isEqualTo("http://www.example.com")
        assertThat(ogTags?.audio).isEqualTo("http://www.example.com/audio.mp3")
        assertThat(ogTags?.description).isEqualTo("The Description")
        assertThat(ogTags?.determiner).isEqualTo("the")
        assertThat(ogTags?.locale).isEqualTo("en_US")
        assertThat(ogTags?.siteName).isEqualTo("The Site Name")
        assertThat(ogTags?.video).isEqualTo("http://www.example.com/video.mp4")
    }

    @Test
    fun parse_imdb() {
        val html = File("src/test/resources/html/imdb_the_rock.html").readText(UTF_8)


        val ogTags = jsoupOgTagParser.parse(html)


        assertThat(ogTags?.title).isEqualTo("The Rock (1996) - IMDb")
        assertThat(ogTags?.type).isEqualTo("video.movie")
        assertThat(ogTags?.image).isEqualTo("https://m.media-amazon.com/images/M/MV5BZDJjOTE0N2EtMmRlZS00NzU0LWE0ZWQtM2Q3MWMxNjcwZjBhXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UY1200_CR90,0,630,1200_AL_.jpg")
        assertThat(ogTags?.url).isEqualTo("http://www.imdb.com/title/tt0117500/")
        assertThat(ogTags?.audio).isNull()
        assertThat(ogTags?.description).isEqualTo("Directed by Michael Bay.  With Sean Connery, Nicolas Cage, Ed Harris, John Spencer. A mild-mannered chemist and an ex-con must lead the counterstrike when a rogue group of military men, led by a renegade general, threaten a nerve gas attack from Alcatraz against San Francisco.")
        assertThat(ogTags?.determiner).isNull()
        assertThat(ogTags?.locale).isNull()
        assertThat(ogTags?.siteName).isEqualTo("IMDb")
        assertThat(ogTags?.video).isNull()
    }

    @Test
    fun parse_trySwift() {
        val html = File("src/test/resources/html/trySwift.html").readText(UTF_8)


        val ogTags = jsoupOgTagParser.parse(html)


        assertThat(ogTags?.title).isEqualTo("try! Swift Conference")
        assertThat(ogTags?.type).isNull()
        assertThat(ogTags?.image).isEqualTo("https://www.tryswift.co/assets/images/group_photo.jpg")
        assertThat(ogTags?.url).isNull()
        assertThat(ogTags?.audio).isNull()
        assertThat(ogTags?.description).isEqualTo("try! Swift is an immersive community gathering about Swift Language Best Practices, Application Development in Swift, Server-Side Swift, Open Source Swift, and the Swift Community taking place around the world in Tokyo, New York, and Bangalore.")
        assertThat(ogTags?.determiner).isNull()
        assertThat(ogTags?.locale).isNull()
        assertThat(ogTags?.siteName).isEqualTo("try! Swift")
        assertThat(ogTags?.video).isNull()
    }
}
