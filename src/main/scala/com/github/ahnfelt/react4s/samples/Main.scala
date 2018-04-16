package com.github.ahnfelt.react4s.samples

import com.github.ahnfelt.react4s._
import org.scalajs.dom

object Main {
    def main(arguments : Array[String]) : Unit = {
        dom.window.onload = _ => {
            val component = Component(MainComponent)
            ReactBridge.renderToDomById(component, "main")
        }
    }
}
