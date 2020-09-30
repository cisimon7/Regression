import io.data2viz.shape.pi
import io.data2viz.viz.JFxVizRenderer
import io.data2viz.viz.Viz
import javafx.scene.canvas.Canvas
import koma.extensions.map
import koma.matrix.Matrix
import koma.pow
import kotlin.math.E
import kotlin.math.sqrt

fun Canvas.visualize(init: Viz.() -> Unit) {
    val viz = io.data2viz.viz.viz(init)
    JFxVizRenderer(this, viz)
    viz.startAnimations()
    viz.render()
}

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun Matrix<Double>.short() = this.map { it.round() }

fun pdfNormal(mean: Double, sd: Double) =
    { x:Double -> koma.exp(-pow((x - mean), 2).div(2*sd*sd)).div(sd* sqrt(2* pi)) }