package com.github.ahnfelt.react4s.samples.timer

import com.github.ahnfelt.react4s._

import scala.scalajs.js
import scala.scalajs.js.timers.SetIntervalHandle

case class TimerComponent() extends Component[NoEmit] {

    val elapsed = State(0)

    var interval : Option[SetIntervalHandle] = None

    override def componentWillRender(get : Get) = {
        if(interval.isEmpty) interval = Some(js.timers.setInterval(1000) {
            elapsed.modify(_ + 1)
        })
    }

    override def componentWillUnmount(get : Get) =
        for(i <- interval) js.timers.clearInterval(i)

    override def render(get : Get) =
        E.h3(Text(get(elapsed) + " seconds elapsed"))

}
