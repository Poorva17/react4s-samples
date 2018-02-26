package com.github.ahnfelt.react4s.samples

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.theme._

case class CodeComponent(code : P[String]) extends Component[NoEmit] {

    override def render(get : Get) : Element = {
        E.pre(
            CodeCss,
            Text(get(code).trim)
        )
    }
}
