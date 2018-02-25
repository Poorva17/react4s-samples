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
                Component(CodeLoaderComponent, "spotify/SpotifyComponent.scala", None),
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
                Component(CodeLoaderComponent, "timer/TimerComponent.scala", None),
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
                Component(CodeLoaderComponent, "todolist/TodoListComponent.scala", None),
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
                Component(CodeLoaderComponent, "treeeditor/TreeRootComponent.scala", None),
                E.div(SpacerCss),
                Text("Then a component to model each node in the tree:"),
                Component(CodeLoaderComponent, "treeeditor/TreeNodeComponent.scala", None),
                E.div(SpacerCss),
                Text("Finally, a helper function to update a list of children based on one of the events:"),
                Component(CodeLoaderComponent, "treeeditor/TreeEvent.scala", Some("object TreeEvent")),
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
                Component(CodeLoaderComponent, "theme/LinkCss.scala", None),
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
                Component(CodeLoaderComponent, "websockets/WebSocketsComponent.scala", None),
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
