import Data.mean
import Data.sample
import Data.sd
import Data.sampleSize
import io.data2viz.geom.Point
import io.data2viz.shape.pi
import koma.create
import koma.extensions.get
import koma.ln
import koma.matrix.Matrix
import koma.pow
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

private const val w = 2.717

private val matX: Matrix<Double> = create(arrayOf(
    DoubleArray(sampleSize) { 1.0 },
    DoubleArray(sampleSize) { cos(w * sample.elementAt(it).x) },
    DoubleArray(sampleSize) { sin(w * sample.elementAt(it).x) }
))

private val matY: Matrix<Double> = create(arrayOf(
    DoubleArray(sample.count()) { sample.elementAt(it).y }
))

val matB: Matrix<Double> = (matX * matX.transpose()).inv() * matX * matY.transpose()

// equation of curve to fit data
val model = { x: Double -> matB[0,0] + matB[1,0]*cos(w*x) + matB[2,0]*sin(w*x) }

val pointPdf = { pt:Point -> pdfNormal(model(pt.x), sd)(pt.y) }
val pointPdfLimit = { pt:Point -> pdfNormal(model(pt.x), sd)(model(pt.x) - 2*sd) }

// y-component of acceleration at a point
val pointAccY = { t:Double -> -pow(w,2)*matB[1,0]*cos(w*t) -pow(w,2)*matB[2,0]*sin(w*t) }

// x-component of acceleration at a point
val pointAccX = { t:Double -> -pow(w,2)*matB[1,0]*sin(w*t) -pow(w,2)*matB[2,0]*cos(w*t) }

// average of y-component of acceleration
val avY = sample.map { pointAccY(it.x) }.sum().div(sample.count())

// average of x-component of acceleration
val avX = sample.map { pointAccX(it.x) }.sum().div(sample.count())

// z-parameter equation
val z = { x:Double -> sqrt(2*sd*sd*ln(x*sd* sqrt(2* pi))) - mean }

//  confidence interval
val CI = 1.96 * sd.div(sqrt(sample.count().toDouble()))