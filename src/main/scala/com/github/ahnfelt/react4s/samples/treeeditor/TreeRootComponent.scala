package com.github.ahnfelt.react4s.samples.treeeditor

import com.github.ahnfelt.react4s._

case class TreeRootComponent(nodes : P[List[TreeNode]]) extends Component[NoEmit] {

    val children = State.of(nodes)

    override def render(get : Get) = E.div(
        E.h3(Text("Tree editor")),
        E.ul(Tags(
            for((item, index) <- get(children).zipWithIndex) yield E.li(
                Component(TreeNodeComponent, item).withHandler { e =>
                    children.modify(TreeNodeComponent.update(_, e, index))
                }
            )
        ))
    )
}
