package com.github.ahnfelt.react4s.samples.uppercase

import com.github.ahnfelt.react4s._

case class UppercaseComponent() extends Component[NoEmit] {

    val text = State("")

    override def render(get : Get) = {
        E.div(
            E.input(A.value(get(text)), A.onChangeText(text.set)),
            E.div(Text(get(text).toUpperCase))
        )
    }

}
