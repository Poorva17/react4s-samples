package com.github.ahnfelt.react4s.samples

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.spotify.SpotifyComponent
import com.github.ahnfelt.react4s.samples.theme._
import com.github.ahnfelt.react4s.samples.timer.TimerComponent
import com.github.ahnfelt.react4s.samples.todolist.TodoListComponent
import com.github.ahnfelt.react4s.samples.treeeditor.{TreeNode, TreeNodeComponent, TreeRootComponent}
import com.github.ahnfelt.react4s.samples.websockets.WebSocketsComponent

case class PageComponent(page : P[Page]) extends Component[NoEmit] {

    override def render(get : Get) : Element = {
        get(page) match {
            case MainPage => renderMainPage()
            case TodoListPage => renderTodoListPage()
            case TreeEditorPage => renderTreeEditorPage()
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

    def renderTreeEditorPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows how to emit messages from components and handle them."),
                E.div(SpacerCss),
                Text("First we'll need a data structure for the tree nodes and for tree node messages:"),
                Component(CodeComponent, """
case class TreeNode(
    label : String,
    children : List[TreeNode]
)

sealed trait TreeEvent
case class SetLabel(label : String) extends TreeEvent
case class SetChildren(children : List[TreeNode]) extends TreeEvent
case object MoveUp extends TreeEvent
case object MoveDown extends TreeEvent
case object Delete extends TreeEvent
                """),
                E.div(SpacerCss),
                Text("Then we'll need a root component, to hold the state of the tree:"),
                Component(CodeComponent, """
case class TreeRootComponent(nodes : P[List[TreeNode]]) extends Component[NoEmit] {

    val children = State.of(nodes)

    override def render(get : Get) = E.div(
        E.h3(Text("Tree editor")),
        E.ul(Tags(
            for((item, index) <- get(children).zipWithIndex) yield E.li(
                Component(TreeNodeComponent, item).withHandler { e =>
                    children.modify(TreeEvent.update(_, e, index))
                }
            )
        ))
    )
}
                """),
                E.div(SpacerCss),
                Text("Then a component to model each node in the tree:"),
                Component(CodeComponent, """
case class TreeNodeComponent(node : P[TreeNode]) extends Component[TreeEvent] {

    override def render(get : Get) =
        E.div(
            E.input(A.onChangeText(t => emit(SetLabel(t))), A.value(get(node).label)),
            renderButton("chevron-up", MoveUp),
            renderButton("chevron-down", MoveDown),
            renderButton("trash", Delete),
            renderButton("plus-circle", SetChildren(get(node).children :+ TreeNode("", List()))),
            renderTodoList(get(node).children),
        )

    def renderButton(icon : String, event : TreeEvent) = E.span(
        S.paddingLeft.px(5),
        E.i(A.className("fa fa-" + icon), LinkCss, A.onLeftClick(_ => emit(event)))
    )

    def renderTodoList(nodes : List[TreeNode]) =
        E.ul(Tags(
            for((item, index) <- nodes.zipWithIndex) yield E.li(
                Component(TreeNodeComponent, item).withHandler { e =>
                    val newChildren = TreeEvent.update(nodes, e, index)
                    emit(SetChildren(newChildren))
                }
            )
        ))
}
                """),
                E.div(SpacerCss),
                Text("Finally, a helper function to update a list of children based on one of the events:"),
                Component(CodeComponent, """
object TreeEvent {

    def update(children : List[TreeNode], event : TreeEvent, index : Int) =
        event match {

    case SetLabel(label) =>
        children.zipWithIndex.map {
            case (c, i) if i == index => c.copy(label = label)
            case (c, _) => c
        }

    case SetChildren(grandChildren) =>
        children.zipWithIndex.map {
            case (c, i) if i == index => c.copy(children = grandChildren)
            case (c, _) => c
        }

    case MoveUp =>
        if(index - 1 < 0) children
        else children.
            updated(index, children(index - 1)).
            updated(index - 1, children(index))

    case MoveDown =>
        if(index + 1 >= children.size) children
        else children.
            updated(index, children(index + 1)).
            updated(index + 1, children(index))

    case Delete =>
        children.take(index) ++
        children.drop(index + 1)

    }
}
                """),
                E.div(SpacerCss),
                sourceLink("treeeditor"),
            ),
            E.div(
                ResultColumnCss,
                Component(TreeRootComponent, List(
                    TreeNode("Pantherinae", List(
                        TreeNode("Panthera", List(
                            TreeNode("Tiger", List()),
                            TreeNode("Lion", List()),
                            TreeNode("Jaguar", List()),
                            TreeNode("Leopard", List()),
                        )),
                    )),
                    TreeNode("Felinae", List(
                        TreeNode("Lynx", List(
                            TreeNode("Canadian lynx", List()),
                            TreeNode("Eurasian lynx", List()),
                            TreeNode("Iberian lynx", List()),
                            TreeNode("Bobcat", List()),
                        )),
                        TreeNode("Puma", List(
                            TreeNode("Cougar", List()),
                            TreeNode("Eyra", List()),
                        )),
                        TreeNode("Acinonyx", List(
                            TreeNode("Cheetah", List()),
                        )),
                    )),
                ))
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
                Text("The class is attached to an element just like a style, attribute or child node:"),
                Component(CodeComponent, """
E.a(LinkCss, A.href("http://www.react4s.org/"), Text("react4s.org"))
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
                Text("This example shows how to use a component that was defined in plain JavaScript+React."),
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
        E.div(
            E.a(A.target("_blank"), A.href(url), LinkCss, Text("Full source code: " + suffix)),
            E.div(SpacerCss),
        )
    }

}
