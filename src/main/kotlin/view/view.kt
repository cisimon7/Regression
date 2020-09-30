package view

import Data
import Data.pdf
import Data.pdfLimit
import Data.sd
import model
import io.data2viz.axis.Orient
import io.data2viz.axis.axis
import io.data2viz.color.Colors
import io.data2viz.color.col
import io.data2viz.geom.size
import javafx.scene.Parent
import points1
import pointsAccX
import pointsDn
import pointsUp
import round
import tornadofx.App
import tornadofx.View
import tornadofx.canvas
import tornadofx.group
import visualize

const val width = 1400.0
const val height = 650.0
const val showAcc = false
const val showSdLine = true
const val pinkDots = true
const val showFit = true

class COMApp : App(MyView::class)

class MyView : View() {
    override val root: Parent = group {
        canvas(width+ 1.5*margins.right, height) {
            visualize {
                size = size(chartHeight, chartWidth)

                group {
                    transform {
                        translate(x = margins.left, y = margins.top)
                    }

                    group {
                        transform {
                            translate(x = -10.0)
                        }
                        axis(Orient.LEFT, yScale) {
                            tickFormat = { it.round().toString() }
                        }
                    }

                    group {
                        transform { translate(x = chartWidth+10) }
                        axis(Orient.RIGHT, yScale2){
                            visible = showAcc
                        }
                    }

                    group {
                        transform {
                            translate(y = chartHeight + 10.0)
                        }
                        axis(Orient.BOTTOM, xScale) {
                            tickFormat = { it.round().toString() }
                        }
                    }

                    group {
                        Data.all.take(Data.sampleSize).forEach { pt ->
                            circle {
                                x = xScale(pt.x)
                                y = yScale(pt.y)
                                radius = 3.0
                                fill = when {
                                    pdf(pt.y) <= pdfLimit -> "#33A7D8".col
                                    pt.y > model(pt.x)+sd -> if (pinkDots) Colors.Web.pink else Colors.Web.crimson
                                    pt.y < model(pt.x)-sd -> if (pinkDots) Colors.Web.pink else Colors.Web.crimson
//                                    pointPdf(pt) <= pointPdfLimit(pt) -> "#33A7D8".col
                                    else -> Colors.Web.crimson
                                }
                            }
                        }
                    }

                    group {
                        path {
                            fill = null
                            stroke = "#FECE3E".col
                            strokeWidth = 4.0

                            if (showFit) {
                                moveTo(xScale(points1[0].x), yScale(points1[0].y))
                                (1 until Data.sample.count()).forEach {
                                    lineTo(xScale(points1[it].x), yScale(points1[it].y))
                                }
                            }
                        }
                        path {
                            fill = null
                            stroke = Colors.Web.blueviolet
                            strokeWidth = 2.0
                            visible = showSdLine

                            moveTo(xScale(pointsUp[0].x), yScale(pointsUp[0].y))
                            (1 until Data.sample.count()).forEach {
                                lineTo(xScale(pointsUp[it].x), yScale(pointsUp[it].y))
                            }

                            moveTo(xScale(pointsDn[0].x), yScale(pointsDn[0].y))
                            (1 until Data.sample.count()).forEach {
                                lineTo(xScale(pointsDn[it].x), yScale(pointsDn[it].y))
                            }
                        }
                    }

                    group {
                        path {
                            fill = null
                            stroke = Colors.Web.springgreen
                            strokeWidth = 1.0
                            visible = showAcc

                            moveTo(xScale(pointsAccX[0].x), yScale2(pointsAccX[0].y))
                            (1 until pointsAccX.count()).forEach {
                                lineTo(xScale(pointsAccX[it].x), yScale2(pointsAccX[it].y))
                            }

                            /*moveTo(xScale(pointsAccY[0].x), yScale(pointsAccY[0].y))
                            (1 until pointsAccY.count()).forEach {
                                lineTo(xScale(pointsAccY[it].x), yScale(pointsAccY[it].y))
                            }*/
                        }
                    }
                }
            }
        }
    }

    init { title="Task 1" }
}
