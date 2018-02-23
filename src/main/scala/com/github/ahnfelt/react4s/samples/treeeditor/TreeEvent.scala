package com.github.ahnfelt.react4s.samples.treeeditor

sealed trait TreeEvent
case class SetLabel(label : String) extends TreeEvent
case class SetChildren(children : List[TreeNode]) extends TreeEvent
case object MoveUp extends TreeEvent
case object MoveDown extends TreeEvent
case object Delete extends TreeEvent


object TreeEvent {

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
