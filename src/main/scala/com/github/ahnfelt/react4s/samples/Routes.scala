package com.github.ahnfelt.react4s.samples

import com.github.werk.router4s.Router

object Routes {

    val path = new Router[Page]

    val router = path(HomePage,
        path("support", SupportPage),
        path("minimal-project", MinimalProjectPage),
        path("gotchas", GotchasPage),
        path("examples", ExamplesPage,
            path("todo-list", TodoListPage),
            path("tree-editor", TreeEditorPage),
            path("css-class", CssClassPage),
            path("spotify-search", SpotifyPage),
            path("timer", TimerPage),
            path("websockets", WebSocketsPage),
            path("react-js", ReactJsPage)
        )
    )

}
