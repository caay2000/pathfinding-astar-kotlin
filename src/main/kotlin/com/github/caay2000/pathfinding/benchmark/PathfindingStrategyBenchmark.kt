package com.github.caay2000.pathfinding.benchmark

import com.github.caay2000.pathfinding.Position
import com.github.caay2000.pathfinding.strategy.AStartPathfindingIterativeStrategy
import com.github.caay2000.pathfinding.strategy.AStartPathfindingTailRecStrategy
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkMode
import kotlinx.benchmark.Measurement
import kotlinx.benchmark.Mode
import kotlinx.benchmark.OutputTimeUnit
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlinx.benchmark.Warmup
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Threads
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 10, time = 5)
@BenchmarkMode(Mode.Throughput)
@Threads(4)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
open class PathfindingStrategyBenchmark {

    private val iterativeStrategy = AStartPathfindingIterativeStrategy()
    private val tailRecStrategy = AStartPathfindingTailRecStrategy()

    private val width: Int = 100
    private val height: Int = 100

    @Benchmark
    fun iterativeStrategy() {

        iterativeStrategy.invoke(
            createCells(width = width, height = height),
            Position(0, 0),
            Position(95, 45)
        )
    }

    @Benchmark
    fun tailRecStrategy() {

        tailRecStrategy.invoke(
            createCells(width = width, height = height),
            Position(0, 0),
            Position(95, 45)
        )
    }

    private fun createCells(width: Int, height: Int): Set<Position> {
        val cells = mutableSetOf<Position>()
        for (x in 0 until width) {
            for (y in 0 until height) {
                cells.add(Position(x, y))
            }
        }
        return cells
    }
}
