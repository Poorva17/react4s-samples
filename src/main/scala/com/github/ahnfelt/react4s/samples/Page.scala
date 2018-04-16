package com.github.ahnfelt.react4s.samples

sealed trait Page
case object HomePage extends Page
case class MinimalProjectPage(parent : HomePage.type = HomePage) extends Page
case class GotchasPage(parent : HomePage.type = HomePage) extends Page
case class SupportPage(parent : HomePage.type = HomePage) extends Page
case class ExamplesPage(parent : HomePage.type = HomePage) extends Page
case class TodoListPage(parent : ExamplesPage = ExamplesPage()) extends Page
case class TreeEditorPage(parent : ExamplesPage = ExamplesPage()) extends Page
case class CssClassPage(parent : ExamplesPage = ExamplesPage()) extends Page
case class SpotifyPage(parent : ExamplesPage = ExamplesPage()) extends Page
case class PortalPage(parent : ExamplesPage = ExamplesPage()) extends Page
case class TimerPage(parent : ExamplesPage = ExamplesPage()) extends Page
case class WebSocketsPage(parent : ExamplesPage = ExamplesPage()) extends Page
case class ReactJsPage(parent : ExamplesPage = ExamplesPage()) extends Page
