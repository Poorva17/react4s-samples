package com.github.ahnfelt.react4s.samples.treeeditor

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.theme.LinkCss

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
                    val newChildren = TreeNodeComponent.update(nodes, e, index)
                    emit(SetChildren(newChildren))
                }
            )
        ))
}

object TreeNodeComponent {

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
                if(index == 0) children
                else children.
                    updated(index, children(index - 1)).
                    updated(index - 1, children(index))
            case MoveDown =>
                if(index >= children.size - 1) children
                else children.
                    updated(index, children(index + 1)).
                    updated(index + 1, children(index))
            case Delete =>
                children.take(index) ++
                    children.drop(index + 1)
        }
}