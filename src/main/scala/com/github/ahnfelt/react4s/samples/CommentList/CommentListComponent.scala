package com.github.ahnfelt.react4s.samples.CommentList

import com.github.ahnfelt.react4s._

case class CommentListComponent() extends Component[NoEmit] {
  val commentAuthor = State("author name")
  val commentBody = State("some comment")

  override def render(get: Get): ElementOrComponent = {
    E.div(
      E.h3(Text("Comment - ")),
      E.p(Text(get(commentBody))),
      E.span(Text(get(commentAuthor)))
    )
  }
}
