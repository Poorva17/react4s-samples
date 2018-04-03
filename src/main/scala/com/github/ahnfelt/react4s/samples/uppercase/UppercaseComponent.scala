package com.github.ahnfelt.react4s.samples.uppercase

import com.github.ahnfelt.react4s._

case class UppercaseComponent() extends Component[NoEmit] {

    val text = State("Hello!")

    override def render(get : Get) = {
        E.span(
            E.input(A.value(get(text)), A.onChangeText(text.set)),
            Text(" .toUpperCase = " + get(text).toUpperCase)
        )
    }

}
