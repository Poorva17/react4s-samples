package com.github.ahnfelt.react4s.samples

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.theme._
import org.scalajs.dom

case class MainComponent() extends Component[NoEmit] {
    
    val router = Routes.router

    val page = State(router.data(dom.window.location.pathname))

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
                        E.div(MenuEntryCss, E.a(Text("Overview"), LinkCss, A.href(router.path(HomePage)))),
                        E.div(MenuEntryCss, E.a(Text("Minimal project"), LinkCss, A.href(router.path(MinimalProjectPage())))),
                        E.div(MenuEntryCss, E.a(Text("Gotchas"), LinkCss, A.href(router.path(GotchasPage())))),
                    ),
                    E.div(
                        E.div(Text("Examples"), MenuCategoryCss),
                        E.div(MenuEntryCss, E.a(Text("Todo list"), LinkCss, A.href(router.path(TodoListPage())))),
                        E.div(MenuEntryCss, E.a(Text("Css class"), LinkCss, A.href(router.path(CssClassPage())))),
                        E.div(MenuEntryCss, E.a(Text("Spotify search"), LinkCss, A.href(router.path(SpotifyPage())))),
                        E.div(MenuEntryCss, E.a(Text("Tree editor"), LinkCss, A.href(router.path(TreeEditorPage())))),
                        E.div(MenuEntryCss, E.a(Text("Timer"), LinkCss, A.href(router.path(TimerPage())))),
                        E.div(MenuEntryCss, E.a(Text("WebSockets"), LinkCss, A.href(router.path(WebSocketsPage())))),
                        E.div(MenuEntryCss, E.a(Text("React.js interop"), LinkCss, A.href(router.path(ReactJsPage())))),
                    ),
                ),
                get(page).map(Component(PageComponent, _)).getOrElse(E.div(ContentColumnCss, Text("Not found")))
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
