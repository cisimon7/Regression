import Data.all
import Data.sample
import Data.sd
import io.data2viz.geom.Point
import io.data2viz.geom.point
import java.nio.file.Files
import java.nio.file.Path
import koma.*
import kotlin.math.sqrt

object Data {
    val all = Files.newBufferedReader(Path.of(Data::class.java.getResource("case4.csv").toURI()))
        .lineSequence()
        .map { it.split(",") }
        .map { point(it.first().toDouble(), it.last().toDouble()) }.toList()

    private val allSize = all.count()

    // calculation of mean of data
    val mean = all.map { it.y }.sum().div(allSize)

    // calculation of standard deviation
    val sd = all.map { pow(it.y - mean, 2) }.sum().div(allSize-3).let { variance -> sqrt(variance) }

    // equation of normal distribution using mean and sd
    val pdf = pdfNormal(mean, sd)
    val pdfLimit = pdf(mean - 3*sd)

    val sample = all.filter { pt -> pdf(pt.y) >= pdfLimit  }

    val sampleSize = sample.count()

    val maxY = all.maxByOrNull { it.y }!!.y
    val minY = all.minByOrNull { it.y }!!.y
    val maxX = all.maxByOrNull { it.x }!!.x
    val minX = all.minByOrNull { it.x }!!.x
}

// points to define fit curve
val points1 = sample.map { it.x }.map { x -> Point(x, model(x).round()) }

// outlier edges curve points+
val pointsUp = all.map { it.x }.map { x -> Point(x, model(x).round() + sd) }
val pointsDn = all.map { it.x }.map { x -> Point(x, model(x).round() - sd) }

// points to define acceleration components
val pointsAccX = sample.map { it.x }.map { t -> Point(t, pointAccX(t)) }
val pointsAccY = sample.map { it.x }.map { t -> Point(t, pointAccY(t)) }
