package com.github.ahnfelt.react4s.samples

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.theme._
import org.scalajs.dom.ext.Ajax

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js

case class MainComponent() extends Component[NoEmit] {

    val page = State[Page](TodoListPage)

    override def render(get : Get) : Element = {
        E.div(
            E.div(
                TopBarCss,
                E.a(Text("React4s"), A.href("/"), BrandTitleCss, LinkCss),
                E.span(Text("The uncomplicated React binding for Scala"), BrandTaglineCss),
            ),
            E.div(
                ColumnContainerCss,
                E.div(
                    MenuColumnCss,
                    E.div(
                        E.div(Text("Usage"), MenuCategoryCss),
                        E.div(Text("Overview"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(OverviewPage))),
                        E.div(Text("Minimal project"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(MinimalProjectPage))),
                        E.div(Text("Gotchas"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(GotchasPage))),
                    ),
                    E.div(
                        E.div(Text("Examples"), MenuCategoryCss),
                        E.div(Text("Todo list"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(TodoListPage))),
                        E.div(Text("Css class"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(CssClassPage))),
                        E.div(Text("Spotify search"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(SpotifyPage))),
                        E.div(Text("Tree editor"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(TreeEditorPage))),
                        E.div(Text("Timer"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(TimerPage))),
                        E.div(Text("WebSockets"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(WebSocketsPage))),
                        E.div(Text("React.js interop"), MenuEntryCss, LinkCss, A.onLeftClick(_ => page.set(ReactJsPage))),
                    ),
                ),
                Component(PageComponent, get(page))
            ),
            E.a(
                S.fontFamily("Verdana"),
                S.fontSize.px(16),
                LinkCss,
                A.href("https://github.com/ahnfelt/react4s"),
                S.position("absolute"),
                S.top.px(15),
                S.right.px(10),
                Text("Fork me on"),
                E.img(
                    S.border.px(0),
                    S.height.px(20),
                    S.marginLeft.px(10),
                    A.src("https://assets-cdn.github.com/images/modules/logos_page/GitHub-Logo.png"),
                    A.alt("Fork me on GitHub"),
                )
            ),
        )
    }
}
