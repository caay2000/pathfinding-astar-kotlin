package com.github.caay2000.pathfinding.strategy

import com.github.caay2000.pathfinding.Path
import com.github.caay2000.pathfinding.PathfindingStrategy
import com.github.caay2000.pathfinding.PathfindingStrategy.Node
import com.github.caay2000.pathfinding.Position

class AStartPathfindingTailRecStrategy : PathfindingStrategy {

    override fun invoke(positions: Set<Position>, source: Position, target: Position): Path {

        val grid = positions.associateWith { Node(it, 1) }

        val result = Grid(grid, source, target).aStar()
        return Path(result, 0F)
    }

    data class GridTile(val position: Position, val heuristic: Float, val cost: Float, val cameFrom: GridTile?) {
        override fun hashCode() = position.hashCode()
        override fun equals(other: Any?) = other is GridTile && position == other.position

        val path: Set<Position>
            get() = path()

        private tailrec fun path(gridTile: GridTile? = this, curPath: Set<Position> = emptySet()): Set<Position> =
            if (gridTile == null) curPath
            else path(gridTile.cameFrom, curPath + gridTile.position)
    }

    data class Grid(val tiles: Map<Position, Node>, val start: Position, val end: Position) {

        private fun neighbours(gridTile: GridTile) = gridTile.position.neighbours
            .filter { position -> position in tiles.keys }
            .map { position -> tiles.getValue(position) }
            .map { node -> GridTile(node.position, node.position distanceTo end, gridTile.cost + node.cost, gridTile) }

        tailrec fun aStar(
            open: Set<GridTile> = setOf(GridTile(start, start distanceTo end, 0.0F, null)),
            closed: List<Position> = emptyList(),
            cur: GridTile =
                if (open.isEmpty()) throw IllegalArgumentException("No Path from Start $start to Finish $end")
                else open.minByOrNull { (_, heuristic, cost) -> heuristic + cost }!!
        ): Set<Position> = if (cur.position == end) cur.path else {
            val newOpens = neighbours(cur)
                .filter { (position) -> position !in closed }
                .filter { (position, _, cost) -> open.find { position == it.position }?.let { cost < it.cost } ?: true }
            aStar(open - cur replace newOpens, closed + cur.position)
        }

        private infix fun <E> Set<E>.replace(e: E) = this - e + e
        private infix fun <E> Set<E>.replace(i: List<E>) = i.fold(this) { acc, e -> acc replace e }
    }
}
