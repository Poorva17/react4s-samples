package com.github.ahnfelt.react4s.samples.treeeditor

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.theme.LinkCss

case class TreeNodeComponent(node: P[TreeNode]) extends Component[TreeEvent] {

  override def render(get: Get) =
    E.div(
      E.input(A.onChangeText(t => emit(SetLabel(t))), A.value(get(node).label)),
      renderButton("chevron-up", MoveUp),
      renderButton("chevron-down", MoveDown),
      renderButton("trash", Delete),
      renderButton("plus-circle",
                   SetChildren(get(node).children :+ TreeNode("", List()))),
      renderTodoList(get(node).children),
    )

  def renderButton(icon: String, event: TreeEvent) = E.span(
    S.paddingLeft.px(5),
    E.i(A.className("fa fa-" + icon), LinkCss, A.onLeftClick(_ => {
      println(s"emmitting event ${event.toString}")
      emit(event)
    }))
  )

  def renderTodoList(nodes: List[TreeNode]) =
    E.ul(
      Tags(
        for ((item, index) <- nodes.zipWithIndex)
          yield
            E.li(
              Component(TreeNodeComponent, item).withHandler { e =>
                println(
                  "withhandler called for event in treenode " + e.toString)
                val newChildren = TreeEvent.update(nodes, e, index)
                println("emitting event set children")
                emit(SetChildren(newChildren))
              }
            )
      ))
}
