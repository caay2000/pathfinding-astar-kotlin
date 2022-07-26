package com.github.caay2000.pathfinding

import com.github.caay2000.pathfinding.strategy.AStartPathfindingIterativeStrategy
import com.github.caay2000.pathfinding.strategy.AStartPathfindingTailRecStrategy
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class AStartPathfindingStrategyTest {

    private val iterative = AStartPathfindingIterativeStrategy()
    private val recursive = AStartPathfindingTailRecStrategy()

    @ParameterizedTest(name = "{index} - {0}")
    @EnumSource(StrategyEnum::class)
    internal fun `from 0,0 to 0,0 correctly`(strategy: StrategyEnum) {

        val path = invoke(
            strategy = strategy,
            positions = createCells(20, 20),
            source = Position(0, 0),
            target = Position(0, 0)
        )
        assertThat(path.cells)
            .hasSize(1)
            .isEqualTo(setOf(Position(0, 0)))
    }

    @ParameterizedTest(name = "{index} - {0}")
    @EnumSource(StrategyEnum::class)
    internal fun `from 0,0 to 0,1 correctly`(strategy: StrategyEnum) {

        val path = invoke(
            strategy = strategy,
            positions = createCells(20, 20),
            source = Position(0, 0),
            target = Position(0, 1)
        )
        assertThat(path.cells)
            .hasSize(2)
            .isEqualTo(setOf(Position(0, 0), Position(0, 1)))
    }

    @ParameterizedTest(name = "{index} - {0}")
    @EnumSource(StrategyEnum::class)
    internal fun `from 0,0 to 0,2 correctly`(strategy: StrategyEnum) {

        val path = invoke(
            strategy = strategy,
            positions = createCells(20, 20),
            source = Position(0, 0),
            target = Position(0, 2)
        )
        assertThat(path.cells)
            .hasSize(3)
            .isEqualTo(setOf(Position(0, 0), Position(0, 1), Position(0, 2)))
    }

    @ParameterizedTest(name = "{index} - {0}")
    @EnumSource(StrategyEnum::class)
    internal fun `from 0,0 to 3,2 correctly`(strategy: StrategyEnum) {

        val path = invoke(
            strategy = strategy,
            positions = createCells(20, 20),
            source = Position(0, 0),
            target = Position(3, 2)
        )

        assertThat(path.cells)
            .hasSize(6)
            .isEqualTo(
                setOf(
                    Position(x = 0, y = 0),
                    Position(x = 1, y = 0),
                    Position(x = 2, y = 0),
                    Position(x = 2, y = 1),
                    Position(x = 3, y = 1),
                    Position(x = 3, y = 2)
                )
            )
    }

    internal enum class StrategyEnum { ITERATIVE, RECURSIVE }

    private fun invoke(strategy: StrategyEnum, positions: Set<Position>, source: Position, target: Position) = when (strategy) {
        StrategyEnum.ITERATIVE -> iterative.invoke(positions, source, target)
        StrategyEnum.RECURSIVE -> recursive.invoke(positions, source, target)
    }

    private val cells = createCells(20, 20)

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
