package com.github.ahnfelt.react4s.samples.websockets

import com.github.ahnfelt.react4s._
import org.scalajs.dom.WebSocket

case class WebSocketsComponent() extends Component[NoEmit] {

    val webSocket = State[Option[WebSocket]](None)
    val log = State(List[String]())
    val text = State("")

    override def componentWillRender(get : Get) = {
        if(get(webSocket).isEmpty) {
            val socket = new WebSocket("wss://echo.websocket.org")
            log.modify(_ :+ "Connecting to: " + socket.url)
            socket.onopen = { e => log.modify(_ :+ "Connected.") }
            socket.onclose = { e => log.modify(_ :+ "Closed: " + e.reason) }
            socket.onmessage = { e => log.modify(_ :+ ("Received: " + e.data)) }
            socket.onerror = { e => log.modify(_ :+ "Error: " + e.message) }
            webSocket.set(Some(socket))
        }
    }

    override def componentWillUnmount(get : Get) = {
        for(socket <- get(webSocket)) socket.close()
    }

    override def render(get : Get) = {
        E.form(
            E.input(A.value(get(text)), A.onChangeText(text.set)),
            E.button(Text("Send")),
            A.onSubmit { e =>
                e.preventDefault()
                for(socket <- get(webSocket)) socket.send(get(text))
                text.set("")
            },
            E.div(Tags(
                for(entry <- get(log)) yield E.div(Text(entry))
            ))
        )
    }

}
