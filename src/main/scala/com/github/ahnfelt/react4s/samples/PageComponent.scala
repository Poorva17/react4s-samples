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
            case HomePage => renderMainPage()
            case MinimalProjectPage(_) => renderMinimalProjectPage()
            case GotchasPage(_) => renderGotchasPage()
            case SupportPage(_) => renderSupportPage()
            case ExamplesPage(_) => renderMainPage()
            case TodoListPage(_) => renderTodoListPage()
            case TreeEditorPage(_) => renderTreeEditorPage()
            case CssClassPage(_) => renderCssClassPage()
            case SpotifyPage(_) => renderSpotifyPage()
            case TimerPage(_) => renderTimerPage()
            case WebSocketsPage(_) => renderWebSocketsPage()
            case ReactJsPage(_) => renderReactJsPage()
        }
    }


    def renderMainPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Component(FrontComponent),
            ),
            E.div(
                ResultColumnCss,
            )
        )
    }

    def renderMinimalProjectPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("React4s projects typically start with 5 files like the ones below, 4 of which you need for any Scala.js application. If you'd rather begin from an existing project, you can simple clone the "),
                E.a(LinkCss, A.href("https://github.com/ahnfelt/react4s-samples"), Text("react4s.org source code")), Text("."),
                E.div(SpacerCss),
                Text("project/plugins.sbt"),
                Component(CodeComponent, """
addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.22")
                """, false),
                E.div(SpacerCss),
                Text("build.sbt"),
                Component(CodeComponent, """
enablePlugins(ScalaJSPlugin)
scalaJSUseMainModuleInitializer := true
scalaVersion := "2.12.4"

resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "com.github.ahnfelt" %%% "react4s" % "0.9.4-SNAPSHOT"
                """, false),
                E.div(SpacerCss),
                Text("index.html"),
                Component(CodeComponent, """
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>example</title>
        <script crossorigin src="https://unpkg.com/react@16/umd/react.production.min.js"></script>
        <script crossorigin src="https://unpkg.com/react-dom@16/umd/react-dom.production.min.js"></script>
    </head>
    <body style="padding: 0; margin: 0">
        <div id="main"></div>
        <script src="target/scala-2.12/example-fastopt.js"></script>
    </body>
</html>
                """, false),
                E.div(SpacerCss),
                Text("src/main/scala/com/example/Main.scala"),
                Component(CodeComponent, """
package com.example

import com.github.ahnfelt.react4s._

object Main {
    def main(arguments : Array[String]) : Unit = {
        val component = Component(MainComponent)
        ReactBridge.renderToDomById(component, "main")
    }
}
                """, false),
                E.div(SpacerCss),
                Text("src/main/scala/com/example/MainComponent.scala"),
                Component(CodeComponent, """
package com.example

import com.github.ahnfelt.react4s._

case class MainComponent() extends Component[NoEmit] {
    override def render(get : Get) = {
        E.div(Text("Hello world!"))
    }
}
                """, false),
                E.div(SpacerCss),
            ),
            E.div(
                ResultColumnCss,
            )
        )
    }

    def renderGotchasPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("All libraries have gotchas, but they're not always documented. While they may be seen to reflect poorly on the library, maybe it's much worse if they're hidden away for the programmer to discover after the fact. With that in mind, here is the complete list of known gotchas for React4s:"),
                E.div(SpacerCss),
                E.div(Text("Functions as props"), S.fontWeight.bold()),
                Text("React4s relies on == to implement shouldComponentUpdate. Since == will only check reference equality for functions, the component will update more often than necessary if passing in functions as arguments, unless you jump through hoops to avoid reallocating functions. All this trouble can be avoided simply by using emit() instead of callbacks."),
                E.div(SpacerCss),
                E.div(Text("Mutable props"), S.fontWeight.bold()),
                Text("React4s relies on == to implement shouldComponentUpdate. For mutable objects, even if == returns true, a rerendering may be necessary, since the object might have been updated regardless. This will result in a stale view. Please use only immutable values as props."),
                E.div(SpacerCss),
                E.div(Text("Tag props"), S.fontWeight.bold()),
                Text("React4s relies on == to implement shouldComponentUpdate. Since tags can contain callbacks, and functions can't be compared for equality, you shouldn't use tags (elements, components, etc.) as props. Instead, create an abstract component with the functionality you need and extend it."),
                E.div(SpacerCss),
                E.div(Text("Updating state in the constructor or during render()"), S.fontWeight.bold()),
                Text("React will throw an exception if you update state during these. Note that it's perfectly OK to update state in event handlers that are attached during render(), eg. A.onSubmit(...), since this delays the state update until the specified event occurs."),
                E.div(SpacerCss),
            ),
            E.div(
                ResultColumnCss,
            )
        )
    }

    def renderSupportPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("Do you have questions about React4s? The quickest way to get an answer is here:"),
                E.div(SpacerCss),
                E.div(E.a(A.href("https://gitter.im/scala-js/scala-js"), LinkCss, Text("gitter.im/scala-js/scala-js"))),
                E.div(SpacerCss),
                Text("Tag @Ahnfelt in your question to get help from the author."),
                E.div(SpacerCss),
                E.div(SpacerCss),
                E.div(Text("Alternatively, you can ask in "), E.a(A.href("https://www.reddit.com/r/scala/"), LinkCss, Text("/r/scala"))),
                E.div(SpacerCss),
            ),
            E.div(
                ResultColumnCss,
            )
        )
    }

    def renderSpotifyPage() = {
        E.div(
            ContentColumnCss,
            E.div(
                CodeColumnCss,
                Text("This example shows how to use debouncing and AJAX."),
                Component(CodeLoaderComponent, "spotify/SpotifyComponent.scala", None, true),
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
                Component(CodeLoaderComponent, "timer/TimerComponent.scala", None, true),
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
                Component(CodeLoaderComponent, "todolist/TodoListComponent.scala", None, true),
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
                """, true),
                E.div(SpacerCss),
                Text("Then we'll need a root component, to hold the state of the tree:"),
                Component(CodeLoaderComponent, "treeeditor/TreeRootComponent.scala", None, true),
                E.div(SpacerCss),
                Text("Then a component to model each node in the tree:"),
                Component(CodeLoaderComponent, "treeeditor/TreeNodeComponent.scala", None, true),
                E.div(SpacerCss),
                Text("Finally, a helper function to update a list of children based on one of the events:"),
                Component(CodeLoaderComponent, "treeeditor/TreeEvent.scala", Some("object TreeEvent"), true),
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
                Component(CodeLoaderComponent, "theme/LinkCss.scala", None, true),
                E.div(SpacerCss),
                Text("The class is attached to an element just like a style, attribute or child node:"),
                Component(CodeComponent, """
E.a(LinkCss, A.href("/"), Text("react4s.org"))
                """, false),
                E.div(SpacerCss),
                sourceLink("theme/LinkCss.scala"),
            ),
            E.div(
                ResultColumnCss,
                E.h3(
                    E.a(A.href("/"), LinkCss, Text("react4s.org"))
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
                Component(CodeLoaderComponent, "websockets/WebSocketsComponent.scala", None, true),
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
""", true),
                E.div(SpacerCss),
                Text("In plain React, the above would look like this:"),
                Component(CodeComponent, """
<FancyButton
    onClick={_ => console.log("Clicked!")}
    labelStyle={{color: "rgb(255, 0, 0)", textTransform: "uppercase"}}
    tip="Click me">
    Submit
</FancyButton>
""", false),
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
