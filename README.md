# Pathfinding A* Algorithm in Kotlin

Different implementations of pathfinding A* algorithm in Kotlin.

Simple code with a simple interface to use wherever you want.

Still not handling wighted maps or obstacles, but with the look of the algorithms, seems to be pretty easy!

```kotlin
interface PathfindingStrategy {
    fun invoke(positions: Set<Position>, source: Position, target: Position): Path
}

data class Position(val x: Int, val y: Int, val cost: Int = 1)
data class Path(val cells: Set<Position>, val cost: Float)
```

There are two different flavours for the same A* algorithm, an Iterative version and a Tail Recursive version

* **Recursive version** Inspired by [Rosetta code](https://rosettacode.org/wiki/A*_search_algorithm#Kotlin)
* **Recursive version** Inspired by [Nicolas Lepage](https://github.com/nlepage) and his [repository](https://github.com/nlepage/kotlin-advent-2018)

## Benchmarking

Iterative version

``` 
415.388 ±(99.9%) 52.733 ops/s [Average]
  (min, avg, max) = (343.910, 415.388, 455.091), stdev = 34.879
  CI (99.9%): [362.655, 468.121] (assumes normal distribution)
```

Recursive version

 ```
 38.714 ±(99.9%) 3.940 ops/s [Average]
  (min, avg, max) = (35.181, 38.714, 42.571), stdev = 2.606
  CI (99.9%): [34.774, 42.654] (assumes normal distribution)
 ```

## Open to improvements

The idea of this repository is to facilitate searching A* kotlin implementations over the net. It took me some time to
find a simple implementation that I could adapt to my code, so I decided to make it public, and maybe help anyone else.

This repository is completely open to improvements, so if you see something to improve just do a PR!

## License

GNU GENERAL PUBLIC LICENSE Version 3, 29 June 2007