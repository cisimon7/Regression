import Data.mean
import Data.sd

fun main() {

    println("B-Matrix = \n${matB.short()}")
    println("mean = ${mean.round()}")
    println("standard deviation = ${sd.round()}")
    println("x-component of acceleration = ${avX.round()}")
    println("y-component of acceleration = ${avY.round()}")
    println("confidence interval = ${CI.round()}")
}

/* Open the show file, to open the plot of the data points */