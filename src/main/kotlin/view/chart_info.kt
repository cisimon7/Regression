package view

import Data
import io.data2viz.scale.Scales
import io.data2viz.viz.Margins

val margins = Margins(40.5, 30.5, 50.5, 70.5)

val chartWidth = width - margins.hMargins
val chartHeight = height - margins.vMargins

val xScale = Scales.Continuous.linear {
    domain = listOf(Data.minX, Data.maxX)
    range = listOf(.0, chartWidth)
}

val yScale = Scales.Continuous.linear {
    domain = listOf(Data.minY, Data.maxY)
    range = listOf(chartHeight, .0) // <- y is mapped in the reverse order (in SVG, javafx (0,0) is top left.
}

val yScale2 = Scales.Continuous.linear {
    domain = listOf(-1_000.0, 1_000.0)
    range = listOf(chartHeight, .0) // <- y is mapped in the reverse order (in SVG, javafx (0,0) is top left.
}