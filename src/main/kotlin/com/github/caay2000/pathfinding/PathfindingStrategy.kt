package com.github.caay2000.pathfinding

interface PathfindingStrategy {

    fun invoke(positions: Set<Position>, source: Position, target: Position): Path

    data class Node(val position: Position, val cost: Int)
}
