package com.github.ahnfelt.react4s.samples.theme

import com.github.ahnfelt.react4s._

object LinkCss extends CssClass(
    S.color(Palette.White.accent),
    S.textDecoration.none(),
    S.cursor.pointer(),
    Css.hover(
        S.textDecoration("underline")
    )
)
