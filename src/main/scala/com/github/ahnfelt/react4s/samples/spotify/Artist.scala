package com.github.ahnfelt.react4s.samples.spotify

import scala.scalajs.js

@js.native
trait Artist extends js.Any {
    val id : String
    val external_urls : ArtistUrl
    val name : String
    val followers : Int
    val genres : js.Array[String]
    val images : js.Array[ArtistImage]
}

@js.native
trait ArtistImage extends js.Any {
    val url : String
    val width : Int
    val height : Int
}

@js.native
trait ArtistUrl extends js.Any {
    val spotify : String
}
