package com.github.ahnfelt.react4s.samples.spotify

import com.github.ahnfelt.react4s.Loader.{Error, Loading, Result}
import com.github.ahnfelt.react4s._
import org.scalajs.dom.ext.Ajax

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

case class SpotifyComponent() extends Component[NoEmit] {

    val query = State("")
    val debouncedQuery = Debounce(this, query, 500)

    val artistsLoader = Loader(this, debouncedQuery) { q =>
        if(q.trim.isEmpty) Future.successful(js.Array[Artist]()) else {
            Ajax.get("/spotify/?q=" + js.URIUtils.encodeURIComponent(q)).
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
            get(artistsLoader) match {
                case Loading() => Text(" loading ... ")
                case Error(_) => Text(" error! ")
                case Result(artists) =>
                    E.div(S.paddingTop.px(10), Tags(
                        for(artist <- artists) yield renderArtist(artist)
                    ))
            }
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
