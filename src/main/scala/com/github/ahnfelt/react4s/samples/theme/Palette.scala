package com.github.ahnfelt.react4s.samples.theme

trait Palette {
    val text : String
    val accent : String
    val background : String
}

object Palette {

    object White extends Palette {
        override val text : String = "rgb(30, 30, 30)"
        override val accent : String = "rgb(202, 88, 152)"
        override val background : String = "rgb(255, 255, 255)"
    }

}