[![Build Status](https://travis-ci.org/iumehara/ogmapper.svg?branch=master)](https://travis-ci.org/iumehara/ogmapper)

# ogmapper: Open Graph Html Tag Parser

ogmapper is a Java/Kotlin library that parses the og meta tags in the head of html pages, based on the [open graph protocol](http://ogp.me/)

The implementation uses the [jsoup](https://github.com/jhy/jsoup) library for fetching and parsing the html for a given url, and returns a custom `OgTags` class consisting of the following properties:

```kotlin
data class OgTags(
    val title: String,
    val type: OgType,
    val url: URL,
    val image: URL?,
    val audio: URL?,
    val description: String?,
    val determiner: OgDeterminer?,
    val locale: String?,
    val siteName: String?,
    val video: URL?
)
```

# Getting Started

Install from Maven Central

### Gradle 
Include the below in your `build.gradle`'s `dependencies` block.
```
implementation 'io.umehara:ogmapper:1.0.0'
```

### Maven
Include the dependency below in the  `pom.xml`'s `<dependencies>` section.
```xml
<dependency>
  <groupId>io.umehara</groupId>
  <artifactId>ogmapper</artifactId>
  <version>1.0.0</version>
</dependency>
```

# Example
use the `JsoupOgMapperFactory` for the default implementation.

```kotlin
  val ogMapper: OgMapper = JsoupOgMapperFactory.build()
  val urlForTheRockOnIMDB = "https://www.imdb.com/title/tt0117500/"
  val ogTags: OgTags? = ogMapper.process(urlForTheRockOnIMDB)
  
  log(ogTags?.title)       // "The Rock (1996) - IMDb"
  log(ogTags?.type)        // "VIDEO"
  log(ogTags?.image)       // "https://m.media-amazon.com/images/M/MV5BZDJjOTE0N2EtMmRlZS00NzU0LWE0ZWQtM2Q3MWMxNjcwZjBhXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_UY1200_CR90,0,630,1200_AL_.jpg"
  log(ogTags?.url)         // "http://www.imdb.com/title/tt0117500/"
  log(ogTags?.description) // "Directed by Michael Bay.  With Sean Connery, Nicolas Cage, Ed Harris, John Spencer. A mild-mannered chemist and an ex-con must lead the counterstrike when a rogue group of military men, led by a renegade general, threaten a nerve gas attack from Alcatraz against San Francisco."
  log(ogTags?.determiner)  // "BLANK"
  log(ogTags?.siteName)    // "IMDb"
```
