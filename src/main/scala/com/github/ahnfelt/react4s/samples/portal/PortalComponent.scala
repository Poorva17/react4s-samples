package com.github.ahnfelt.react4s.samples.portal

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.theme.CodeCss
import org.scalajs.dom

case class PortalComponent() extends Component[NoEmit] {

    var showDialog = State(false)
    var container : Option[dom.Node] = None

    override def componentWillRender(get : Get) = {
        if(container.isEmpty) {
            container = Some(dom.document.createElement("div"))
            for(c <- container) dom.document.getElementById("modal").appendChild(c)
        }
    }

    override def componentWillUnmount(get : Get) = {
        for(c <- container) dom.document.getElementById("modal").removeChild(c)
        container = None
    }

    override def render(get : Get) =
        E.div(
            E.button(
                A.`type`("button"),
                A.onLeftClick(_ => showDialog.set(true)),
                Text("Show dialog")
            ),
            Tags(
                for(c <- container if get(showDialog))
                    yield Portal(renderDialog(), c)
            )
        )

    def renderDialog() = E.div(
        S.position.absolute(),
        S.top.px(20),
        S.bottom.px(20),
        S.left.px(30),
        S.right.px(30),
        CodeCss,
        Text("\n\n    This is a dialog\n\n    It lives in the 'modal' top-level div\n\n    Click to close"),
        A.onLeftClick(_ => showDialog.set(false))
    )

}
