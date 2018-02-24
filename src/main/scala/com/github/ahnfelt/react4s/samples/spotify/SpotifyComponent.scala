package com.github.ahnfelt.react4s.samples.spotify

import com.github.ahnfelt.react4s._
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

case class SpotifyComponent() extends Component[NoEmit] {

    val query = State("")
    val debouncedQuery = Debounce(this, query, 500)

    val artists = Loader(this, debouncedQuery) { q =>
        if(q.trim.isEmpty) Future.successful(js.Array[Artist]()) else {
            val proxy = "http://www.react4s.org/spotify/?q="
            Ajax.get(proxy + js.URIUtils.encodeURIComponent(q)).
                map { ajax =>
                    js.JSON.parse(ajax.responseText).
                        artists.items.
                        asInstanceOf[js.Array[Artist]]
                }
        }
    }

    override def render(get : Get) = {
        E.div(
            E.h3(Text("Search in Spotify artists")),
            E.input(A.value(get(query)), A.onChangeText(query.set)),
            Text(" loading ... ").when(get(artists.loading)),
            Text(" error! ").when(get(artists.error).nonEmpty),
            E.div(S.paddingTop.px(10), Tags(
                for(artist <- get(artists).toList.flatten)
                    yield renderArtist(artist)
            ))
        )
    }

    def renderArtist(artist : Artist) = {
        E.a(
            ArtistCss,
            A.target("_blank"),
            A.href(artist.external_urls.spotify),
            E.img(
                S.maxWidth.percent(100),
                S.maxHeight.percent(80),
                A.src(artist.images.headOption.map(_.url).getOrElse(""))
            ),
            E.div(Text(artist.name))
        )
    }

}
