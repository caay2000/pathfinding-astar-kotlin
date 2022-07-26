package com.github.caay2000.pathfinding

import kotlin.math.sqrt

data class Path(val cells: Set<Position>, val cost: Float)

data class Position(val x: Int, val y: Int, val cost: Int = 1) {

    fun distance(destination: Position): Float {
        val x = destination.x - x
        val y = destination.y - y
        return sqrt((x * x + y * y).toDouble()).toFloat()
    }

    infix fun distanceTo(a: Position) = this.distance(a)

    private fun sum(position: Position): Position = copy(x = x + position.x, y = y + position.y)

    val neighbours: Set<Position>
        get() = setOf(
            this.sum(Position(1, 0)),
            this.sum(Position(0, 1)),
            this.sum(Position(-1, 0)),
            this.sum(Position(0, 1))
        )
}
