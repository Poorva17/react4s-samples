package com.github.ahnfelt.react4s.samples.treeeditor

sealed trait TreeEvent
case class SetLabel(label : String) extends TreeEvent
case class SetChildren(children : List[TreeNode]) extends TreeEvent
case object MoveUp extends TreeEvent
case object MoveDown extends TreeEvent
case object Delete extends TreeEvent
