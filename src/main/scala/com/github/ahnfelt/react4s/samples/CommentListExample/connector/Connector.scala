package com.github.ahnfelt.react4s.samples.CommentListExample.connector

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.CommentListExample.Notification.Notification
import com.github.ahnfelt.react4s.samples.CommentListExample.commentList.{CommentBox, NotificationEvent}

case class Connector() extends Component[NoEmit] {

  val noOfComments = State(0)

  override def render(get: Get): ElementOrComponent = {
    E.div(
      E.div(
        Component(CommentBox).withHandler { e =>
          noOfComments.set(NotificationEvent.update(get, e))
        }
      ),
      E.div(
        Component(Notification, get(noOfComments))
      )
    )
  }
}
