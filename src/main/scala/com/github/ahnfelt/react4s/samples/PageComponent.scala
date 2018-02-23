package com.github.ahnfelt.react4s.samples

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.spotify.SpotifyComponent
import com.github.ahnfelt.react4s.samples.theme._
import com.github.ahnfelt.react4s.samples.timer.TimerComponent
import com.github.ahnfelt.react4s.samples.todolist.TodoListComponent
import com.github.ahnfelt.react4s.samples.websockets.WebSocketsComponent

case class PageComponent(page : P[Page]) extends Component[NoEmit] {

    override def render(get : Get) : Element = {
        get(page) match {
            case MainPage => renderMainPage()
            case TodoListPage => renderTodoListPage()
            case CssClassPage => renderCssClassPage()
            case SpotifyPage => renderSpotifyPage()
            case TimerPage => renderTimerPage()
            case WebSocketsPage => renderWebSocketsPage()
            case ReactJsPage => renderReactJsPage()
        }
    }


    def renderMainPage() = {
        E.div(
            ContentColumnCss,
        )
    }

    def renderSpotifyPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows how to use debouncing and AJAX."),
                Component(CodeComponent, """
case class SpotifyComponent() extends Component[NoEmit] {

    val query = State("")
    val debouncedQuery = Debounce(this, query, 500)

    val artists = Loader(this, debouncedQuery) { q =>
        if(q.trim.isEmpty) Future.successful(js.Array[Artist]()) else {
            val proxy = "http://show.ahnfelt.net/react4s-spotify/?q="
            Ajax.get(proxy + js.URIUtils.encodeURIComponent(q)).
                map { ajax =>
                    js.JSON.parse(ajax.responseText).
                        artists.items.
                        asInstanceOf[js.Array[Artist]]
                }
        }
    }

    override def render(get : Get) = {
        E.form(
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
                """),
                E.div(SpacerCss),
                sourceLink("spotify"),
            ),
            E.div(
                ResultColumnCss,
                Component(SpotifyComponent)
            )
        )
    }

    def renderTimerPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows how many seconds elapsed so far."),
                Component(CodeComponent, """
case class TimerComponent() extends Component[NoEmit] {

    val elapsed = State(0)

    var interval : Option[SetIntervalHandle] = None

    override def componentWillRender(get : Get) = {
        if(interval.isEmpty) interval = Some(js.timers.setInterval(1000) {
            elapsed.modify(_ + 1)
        })
    }

    override def componentWillUnmount(get : Get) =
        for(i <- interval) js.timers.clearInterval(i)

    override def render(get : Get) =
        E.div(Text(get(elapsed) + " seconds elapsed"))

}
                """),
                E.div(SpacerCss),
                sourceLink("timer"),
            ),
            E.div(
                ResultColumnCss,
                Component(TimerComponent)
            )
        )
    }

    def renderTodoListPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows a simple TODO list."),
                Component(CodeComponent, """
case class TodoListComponent() extends Component[NoEmit] {

    val text = State("")
    val items = State(List[String]())

    override def render(get : Get) =
        E.div(
            E.h3(Text("TODO")),
            renderTodoList(get(items)),
            E.form(
                E.input(A.onChangeText(text.set), A.value(get(text))),
                E.button(Text("Add #" + (get(items).length + 1))),
                A.onSubmit { e =>
                    e.preventDefault()
                    items.modify(_ :+ get(text))
                    text.set("")
                }
            )
        )

    def renderTodoList(items : List[String]) =
        E.ul(Tags(for(item <- items) yield E.li(Text(item))))

}
                """),
                E.div(SpacerCss),
                sourceLink("todolist"),
            ),
            E.div(
                ResultColumnCss,
                Component(TodoListComponent)
            )
        )
    }

    def renderCssClassPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows a simple CSS class with a :hover effect. All links on this site uses it."),
                Component(CodeComponent, """
object LinkCss extends CssClass(
    S.color.rgb(202, 88, 152),
    S.textDecoration.none(),
    S.cursor.pointer(),
    Css.hover(
        S.textDecoration("underline")
    )
)
                """),
                E.div(SpacerCss),
                sourceLink("theme/LinkCss.scala"),
            ),
            E.div(
                ResultColumnCss,
                E.h3(
                    E.a(A.href("http://www.react4s.org/"), LinkCss, Text("react4s.org"))
                )
            )
        )
    }

    def renderWebSocketsPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows how to use web sockets."),
                Component(CodeComponent, """
case class WebSocketsComponent() extends Component[NoEmit] {

    val webSocket = State[Option[WebSocket]](None)
    val log = State(List[String]())
    val text = State("")

    override def componentWillRender(get : Get) = {
        if(get(webSocket).isEmpty) {
            val socket = new WebSocket("wss://echo.websocket.org")
            log.modify(_ :+ "Connecting to: " + socket.url)
            socket.onopen = { e => log.modify(_ :+ "Connected.") }
            socket.onclose = { e => log.modify(_ :+ "Closed: " + e.reason) }
            socket.onmessage = { e => log.modify(_ :+ ("Received: " + e.data)) }
            socket.onerror = { e => log.modify(_ :+ "Error: " + e.message) }
            webSocket.set(Some(socket))
        }
    }

    override def componentWillUnmount(get : Get) = {
        for(socket <- get(webSocket)) socket.close()
    }

    override def render(get : Get) = {
        E.form(
            E.input(A.value(get(text)), A.onChangeText(text.set)),
            E.button(Text("Send")),
            A.onSubmit { e =>
                e.preventDefault()
                for(socket <- get(webSocket)) socket.send(get(text))
                text.set("")
            },
            E.div(Tags(
                for(entry <- get(log)) yield E.div(Text(entry))
            ))
        )
    }

}
                """),
                E.div(SpacerCss),
                sourceLink("websockets"),
            ),
            E.div(
                ResultColumnCss,
                Component(WebSocketsComponent)
            )
        )
    }

    def renderReactJsPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows how to use a component that was defined in plan React in JavaScript."),
                Component(CodeComponent, """
object FancyButton extends JsComponent(js.Dynamic.global.FancyButton)

FancyButton(
    J("onClick", {_ => println("Clicked!")}),
    J("labelStyle", S.color.rgb(255, 0, 0), S("text-transform", "uppercase")),
    J("tip", "Click me"),
    Text("Submit")
)
"""),
                E.div(SpacerCss),
                Text("In plain React, the above would look like this:"),
                Component(CodeComponent, """
<FancyButton
    onClick={_ => console.log("Clicked!")}
    labelStyle={{color: "rgb(255, 0, 0)", textTransform: "uppercase"}}
    tip="Click me">
    Submit
</FancyButton>
"""),
                E.div(SpacerCss),
            ),
            E.div(
                ResultColumnCss,
                Text("No preview")
            )
        )
    }

    def sourceLink(suffix : String) = {
        val prefix = "https://github.com/Ahnfelt/react4s-samples/blob/master/src/main/scala/com/github/ahnfelt/react4s/samples/"
        val url = prefix + suffix
        E.a(A.target("_blank"), A.href(url), LinkCss, Text("Full source code: " + suffix))
    }

}
