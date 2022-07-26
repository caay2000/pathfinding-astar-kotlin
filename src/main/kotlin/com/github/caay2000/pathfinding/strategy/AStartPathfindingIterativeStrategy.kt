package com.github.caay2000.pathfinding.strategy

import com.github.caay2000.pathfinding.Path
import com.github.caay2000.pathfinding.PathfindingStrategy
import com.github.caay2000.pathfinding.PathfindingStrategy.Node
import com.github.caay2000.pathfinding.Position

class AStartPathfindingIterativeStrategy : PathfindingStrategy {

    override fun invoke(positions: Set<Position>, source: Position, target: Position): Path {
        return privateInvoke(
            positions = Grid(positions.associateWith { Node(it, it.cost) }),
            source = source,
            target = target
        )
    }

    private fun privateInvoke(positions: Grid, source: Position, target: Position): Path {

        val openVertices = mutableSetOf(source)
        val closedVertices = mutableSetOf<Position>()
        val costFromStart = mutableMapOf(source to 0)
        val estimatedTotalCost = mutableMapOf(source to source.distance(target))
        val cameFrom = mutableMapOf<Position, Position>()

        while (openVertices.size > 0) {
            val currentPos = openVertices.minBy { estimatedTotalCost.getValue(it) }
            if (currentPos == target) {
                val path = generatePath(currentPos, cameFrom)
                val cost = estimatedTotalCost.getValue(target)
                return Path(path, cost)
            }
            openVertices.remove(currentPos)
            closedVertices.add(currentPos)
            positions.getNeighbours(currentPos)
                .filterNot { closedVertices.contains(it) }
                .forEach { neighbour ->
                    val score = costFromStart.getValue(currentPos) + positions.moveCost(currentPos, neighbour)
                    if (score < costFromStart.getOrDefault(neighbour, MAX_SCORE)) {
                        if (!openVertices.contains(neighbour)) {
                            openVertices.add(neighbour)
                        }
                        cameFrom[neighbour] = currentPos
                        costFromStart[neighbour] = score
                        estimatedTotalCost[neighbour] = score + neighbour.distance(target)
                    }
                }
        }
        throw IllegalArgumentException("No Path from Start $source to Finish $target")
    }

    private fun generatePath(currentPos: Position, cameFrom: Map<Position, Position>): Set<Position> {
        val path = mutableListOf(currentPos)
        var current = currentPos
        while (cameFrom.containsKey(current)) {
            current = cameFrom.getValue(current)
            path.add(0, current)
        }
        return path.toSet()
    }

    data class Grid(val positions: Map<Position, Node>) {

        fun getNeighbours(position: Position): Set<Position> =
            position.neighbours
                .filter { positions.containsKey(it) }
                .map { positions.getValue(it).position }
                .toSet()

        fun moveCost(currentPos: Position, neighbour: Position): Int {
            return currentPos.cost + neighbour.cost
        }
    }

    companion object {
        private const val MAX_SCORE = 99999999
    }
}
