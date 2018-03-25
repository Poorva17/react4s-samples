package com.github.ahnfelt.react4s.samples

import com.github.ahnfelt.react4s._
import com.github.ahnfelt.react4s.samples.theme._

case class FrontComponent() extends Component[NoEmit] {

    override def render(get : Get) : Element = {
        E.div(
            E.h1(HeadingCss,
                Text("Component based UI")
            ),
            E.p(
                Text("Specify webapps in terms of components, which are self-contained, reusable units of UI. "),
                Text("Components can be built from smaller ones via composition, and rendering is expressed as a pure function from data to virtual DOM. "),
                Text("It's an approach that scales with your application. "),
            ),
            E.h1(HeadingCss,
                Text("No macros, no implicits, no boilerplate")
            ),
            E.p(
                Text("React4s is straightforward in the sense that it uses no macros and no implicits. "),
            ),
            E.p(
                Text("Yet, you'll likely find the examples to be shorter than the equivalent examples in comparable libraries, for Scala or otherwise. "),
            ),
            E.h1(HeadingCss,
                Text("Performance by default")
            ),
            E.p(
                Text("Without shouldComponentUpdate(), React.js recreates the entire virtual DOM for every event - eg. every letter you type in a text field - which leads to input lag. "),
                Text("In plain React.js, the solution is to memoize callbacks, which leads to awkward, unidiomatic code. "),
            ),
            E.p(
                Text("In React4s, there are no callbacks to memoize. "),
                Text("React4s takes advantage of immutability and structural equality to implement shouldComponentUpdate() automatically. "),
                Text("No code required. "),
            ),
            E.p(
                Text("This means that only the relevant path from the root of the view to the updated branch is recomputed. "),
                Text("When the view is balanced, the speedup is exponential: O(log(n)) instead of O(n). "),
            ),
            E.h1(HeadingCss,
                Text("No callbacks - just emit()")
            ),
            E.p(
                Text("React4s has an Elm/Redux-like emit() system that lets you write pure components with no internal state. "),
                Text("Instead of updating state locally, you can choose to emit() a message to the parent component, telling it how to update. "),
            ),
            E.p(
                Text("Using this, you have the option of making your entire view pure, and only have state at the very top of your view. "),
                Text("Or you can mix and match. "),
            ),
            E.h1(HeadingCss,
                Text("Simple lifecycle, declarative effects")
            ),
            E.p(
                Text("In React4s, the lifecycle starts with your constructor, where you can set up state and attachables. "),
                Text("Then componentWillRender() and render() will be called. These two methods are called in that order every time the state is updated or props change. "),
                Text("Finally componentWillUnmount() is called just before the component is removed from the DOM. "),
            ),
            E.img(
                A.src("https://cloud.githubusercontent.com/assets/78472/22898855/198ae112-f229-11e6-8784-b854dd679f50.png"),
                CodeCss,
            ),
            E.p(
                Text("You can factor out code that deals with setting up and tearing down AJAX, timers, global event listeners and so on into Attachables, which can be declarative and reusable. "),
                Text("One example is the included Loader, which declaratively loads data from any source that can provide a Future. "),
            ),
            E.h1(HeadingCss,
                Text("CSS support with automatic namespacing")
            ),
            E.p(
                Text("You can define CSS classes directly in Scala, and they will be automatically be added to the DOM on use. "),
                Text("They will receive a hash suffix to avoid collisions with classes of the same name in other packages, eg. MyCssClass-ab12cd34. "),
            ),
            E.p(
                Text("In this way, you can put your CSS classes next to the components that need them. "),
                Text("Pseudo-selectors like :hover and :active are supported, as well as media queries, animations and more. "),
            ),
            E.h1(HeadingCss,
                Text("Small codebase, no dependencies")
            ),
            E.p(
                Text("React4s is only ~1k lines of code, and has no dependencies beyond React.js. "),
                Text("It's lightweight, easy to fork, and contributions are very welcome. "),
            ),
            E.h1(HeadingCss,
                Text("Getting started")
            ),
            E.p(
                Text("Check out the examples on the left hand side. Then clone ahnfelt/react4s-samples on GitHub or follow the minimal project guide. "),
            ),
            E.div(SpacerCss),
            E.div(SpacerCss),
        )
    }

}

object HeadingCss extends CssClass(
    S.margin.px(0),
    S.fontSize.px(25),
    S.fontWeight("normal"),
    S.color(Palette.accent),
    Css.selector(":not(:first-child)",
        S.paddingTop.px(20)
    )
)
