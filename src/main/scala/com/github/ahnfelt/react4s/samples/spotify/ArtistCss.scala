package com.github.ahnfelt.react4s.samples.spotify

import com.github.ahnfelt.react4s.samples.theme.LinkCss
import com.github.ahnfelt.react4s.{CssClass, S}

object ArtistCss extends CssClass(
    LinkCss,
    S.backgroundColor("white"),
    S.boxShadow("0 1px 3px rgba(0, 0, 0, 0.3)"),
    S.display.inlineBlock(),
    S.verticalAlign.bottom(),
    S.boxSizing.borderBox(),
    S.margin.px(10),
    S.textAlign.center(),
    S.padding.px(5),
    S.width.px(140),
    S.height.px(140),
    S.overflow("hidden"),
)
