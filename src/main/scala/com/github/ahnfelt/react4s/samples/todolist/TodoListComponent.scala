package com.github.ahnfelt.react4s.samples.todolist

import com.github.ahnfelt.react4s._

import scala.scalajs.js
import scala.scalajs.js.timers.SetIntervalHandle

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
